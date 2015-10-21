package net.longfalcon.newsj;

import net.longfalcon.newsj.model.BinaryBlacklistEntry;
import net.longfalcon.newsj.model.Group;
import net.longfalcon.newsj.persistence.BinaryBlacklistDAO;
import net.longfalcon.newsj.util.Defaults;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.nntp.Article;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: Sten Martinez
 * Date: 10/14/15
 * Time: 4:32 PM
 */
@Service
public class Blacklist {
    private static List<BinaryBlacklistEntry> blacklistEntriesCache = null;

    private static final Log _log = LogFactory.getLog(Blacklist.class);

    private BinaryBlacklistDAO binaryBlacklistDAO;

    public BinaryBlacklistDAO getBinaryBlacklistDAO() {
        return binaryBlacklistDAO;
    }

    public void setBinaryBlacklistDAO(BinaryBlacklistDAO binaryBlacklistDAO) {
        this.binaryBlacklistDAO = binaryBlacklistDAO;
    }

    public boolean isBlackListed(Article article, Group group) {
        boolean isBlackListed = false;
        String[] fields = new String[4];
        fields[0] = "";
        fields[Defaults.BLACKLIST_FIELD_SUBJECT] = article.getSubject();
        fields[Defaults.BLACKLIST_FIELD_FROM] = article.getFrom();
        fields[Defaults.BLACKLIST_FIELD_MESSAGEID] = article.getArticleId();
        List<BinaryBlacklistEntry> blacklistEntries = getCachedBlacklist();
        for (BinaryBlacklistEntry blacklistEntry : blacklistEntries) {
            Pattern groupPattern = Pattern.compile("^" +blacklistEntry.getGroupName() + "$", Pattern.CASE_INSENSITIVE);
            Matcher groupNameMatcher = groupPattern.matcher(group.getName());
            if (groupNameMatcher.matches()) {
                Pattern blacklistPattern = Pattern.compile(blacklistEntry.getRegex(), Pattern.CASE_INSENSITIVE);
                String matcherText = fields[blacklistEntry.getMsgCol()];
                Matcher blacklistMatcher = blacklistPattern.matcher(matcherText);
                if (blacklistEntry.getOpType() == 1) {
                    // remove what matches
                    if (blacklistMatcher.find()) {
                        isBlackListed = true;
                    }
                } else if (blacklistEntry.getOpType() == 2) {
                    // remove all that doesnt match
                    if (!blacklistMatcher.find()) {
                        isBlackListed = true;
                    }
                }
            }
        }

        return isBlackListed;
    }

    /**
     * call after making changes to the black list
     */
    public void invalidateCache() {
        blacklistEntriesCache = null;
    }

    private List<BinaryBlacklistEntry> getCachedBlacklist() {
        if (blacklistEntriesCache == null) {
            blacklistEntriesCache = binaryBlacklistDAO.findAllBinaryBlacklistEntries(true);
        }
        return blacklistEntriesCache;
    }
}
