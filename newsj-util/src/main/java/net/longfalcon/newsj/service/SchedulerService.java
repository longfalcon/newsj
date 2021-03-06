/*
 * Copyright (c) 2016. Sten Martinez
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package net.longfalcon.newsj.service;

import net.longfalcon.newsj.Backfill;
import net.longfalcon.newsj.job.BackfillGroupJob;
import net.longfalcon.newsj.job.JobConfigKeys;
import net.longfalcon.newsj.model.JobConfig;
import net.longfalcon.newsj.persistence.JobConfigDAO;
import net.longfalcon.newsj.util.DateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.CronScheduleBuilder;
import org.quartz.DateBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.ScheduleBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * User: Sten Martinez
 * Date: 9/9/16
 * Time: 2:17 PM
 */
public class SchedulerService {
    public static final String TRIGGER_GROUP = "NewsJ-1";
    public static final String UPDATE_TRIGGER_NAME = "updateTrigger";
    private static final Log _log = LogFactory.getLog(SchedulerService.class);
    private JobConfigDAO jobConfigDAO;
    private Scheduler scheduler;
    private JobDetail updateJobDetail;
    private Backfill backfill;
    private JobLogService jobLogService;

    public void destroy() {
        try {
            scheduler.shutdown(false);
        } catch (SchedulerException e) {
            _log.error(e.toString(), e);
        }
    }

    public void init() {
        try {
            Trigger trigger = getUpdateJobTrigger();
            if (trigger != null) {
                Date nextRun = scheduler.scheduleJob(updateJobDetail, trigger);
                _log.info(trigger.getKey().getName() + " is scheduled for " + DateUtil.formatDefaultDate(nextRun));
            }
        } catch (SchedulerException e) {
            _log.error(e.toString(), e);
        }
    }

    private Trigger getUpdateJobTrigger() {
        JobConfig updateJobConfig = jobConfigDAO.getJobConfigByJobName(JobConfigKeys.UPDATE_JOB_KEY);
        Trigger trigger;
        ScheduleBuilder scheduleBuilder;
        if (updateJobConfig.getJobFrequency().equals(JobConfigKeys.FREQ_SCHEDULED)) {
            String cronExpression = updateJobConfig.getFrequencyConfig();
            scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
        } else if (updateJobConfig.getJobFrequency().equals(JobConfigKeys.FREQ_PERIODIC)) {
            int interval = Integer.parseInt(updateJobConfig.getFrequencyConfig());
            scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInMilliseconds(interval)
                    .repeatForever();
        } else {
            return null;
        }

        // always start 2 minutes after startup
        trigger = TriggerBuilder.newTrigger()
                .withIdentity(UPDATE_TRIGGER_NAME, TRIGGER_GROUP)
                .startAt(DateBuilder.futureDate(2, DateBuilder.IntervalUnit.MINUTE))
                .withSchedule(scheduleBuilder)
                .build();
        return trigger;
    }

    public void scheduleBackfillJob(String groupName) {
        try {
            Map<String,Object> jobDataMap = new HashMap<>();
            jobDataMap.put("groupName", groupName);
            jobDataMap.put("backfill", backfill);
            jobDataMap.put("jobLogService", jobLogService);
            JobDetail backfillJobDetail = JobBuilder.newJob().ofType(BackfillGroupJob.class)
                    .usingJobData(new JobDataMap(jobDataMap))
                    .build();
             Trigger trigger = TriggerBuilder.newTrigger()
                     .withIdentity("BACKFILL-" + groupName, "BACKFILL_JOBS")
                     .startNow()
                     .build();
            scheduler.scheduleJob(backfillJobDetail,trigger);
        } catch (SchedulerException e) {
            _log.error(e.toString(), e);
        }
    }

    public void reset(String jobKey) {
        if (JobConfigKeys.UPDATE_JOB_KEY.equals(jobKey)) {
            Trigger newTrigger = getUpdateJobTrigger();
            try {
                TriggerKey triggerKey = new TriggerKey(UPDATE_TRIGGER_NAME, TRIGGER_GROUP);
                if (newTrigger != null) {
                    if (isUpdateJobScheduled()) {
                        Date nextRun = scheduler.rescheduleJob(triggerKey, newTrigger);
                        _log.info(newTrigger.getKey().getName() + " is scheduled for " + DateUtil.formatDefaultDate(nextRun));
                    } else {
                        Date nextRun = scheduler.scheduleJob(updateJobDetail, newTrigger);
                        _log.info(newTrigger.getKey().getName() + " is scheduled for " + DateUtil.formatDefaultDate(nextRun));
                    }
                } else {
                    if (isUpdateJobScheduled()) {
                        scheduler.unscheduleJob(triggerKey);
                        _log.info(triggerKey.getName() + " has been unscheduled");
                    }
                }
            } catch (SchedulerException e) {
                _log.error(e.toString(), e);
            }
        }
    }

    public void pauseUpdateTask() {
        try {
            scheduler.pauseTrigger(new TriggerKey(UPDATE_TRIGGER_NAME, TRIGGER_GROUP));

        } catch (SchedulerException e) {
            _log.error(e.toString(), e);
        }
    }

    public boolean isUpdateJobScheduled() {
        try {
            return scheduler.checkExists(new TriggerKey(UPDATE_TRIGGER_NAME, TRIGGER_GROUP));
        } catch (SchedulerException e) {
            _log.error(e.toString(), e);
            return false;
        }
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public JobDetail getUpdateJobDetail() {
        return updateJobDetail;
    }

    public void setUpdateJobDetail(JobDetail updateJobDetail) {
        this.updateJobDetail = updateJobDetail;
    }

    public JobConfigDAO getJobConfigDAO() {
        return jobConfigDAO;
    }

    public void setJobConfigDAO(JobConfigDAO jobConfigDAO) {
        this.jobConfigDAO = jobConfigDAO;
    }

    public Backfill getBackfill() {
        return backfill;
    }

    public void setBackfill(Backfill backfill) {
        this.backfill = backfill;
    }

    public JobLogService getJobLogService() {
        return jobLogService;
    }

    public void setJobLogService(JobLogService jobLogService) {
        this.jobLogService = jobLogService;
    }
}
