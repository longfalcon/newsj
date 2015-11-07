/*
 * Copyright (c) 2015. Sten Martinez
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

import net.longfalcon.newsj.Nzb;
import net.longfalcon.newsj.fs.model.Directory;
import net.longfalcon.newsj.fs.model.DirectoryImpl;
import net.longfalcon.newsj.model.Release;
import net.longfalcon.newsj.persistence.ReleaseDAO;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;

/**
 * User: Sten Martinez
 * Date: 10/23/15
 * Time: 12:45 PM
 */
public class NzbServiceTest {

    Directory directory;

    @Before
    public void setUp() {
        directory = new DirectoryImpl(new File(""));
    }

    @Ignore
    @Test
    public void testWriteNZB() {
        ApplicationContext context =
                new ClassPathXmlApplicationContext(new String[] {"application-context.xml"});
        Nzb nzbService = (Nzb) context.getBean("nzb");
        ReleaseDAO releaseDAO = (ReleaseDAO) context.getBean("releaseDAO");
        Release release = releaseDAO.findByReleaseId(130);
        System.out.println("release name " + release.getName());
        nzbService.writeNZBforReleaseId(release, directory, false);

    }
}
