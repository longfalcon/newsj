-- test data for ReleaseCommentDAO test
INSERT INTO RELEASES(ID, NAME_, SEARCHNAME, TOTALPART, GROUPID, SIZE_, POSTDATE, ADDDATE, GUID, FROMNAME, COMPLETION,
                     CATEGORYID, REGEXID, RAGEID, SERIESFULL, SEASON, EPISODE, TVTITLE, TVAIRDATE, IMDBID, MUSICINFOID,
                     CONSOLEINFOID, REQID, GRABS, COMMENTS, PASSWORDSTATUS)
VALUES (2,'Fifty.Shades.of.Grey.2015.AC3.BDRiP.XviD-pnc','Fifty.Shades.of.Grey.2015.AC3.BDRiP.XviD-pnc',71,2,1028487147,
        TIMESTAMP('2015-01-23', '07.18.26'),TIMESTAMP ('2015-01-23', '21.38.16'),'2b4998c50f4f9a1a8605ca575b7b2082',
        '"Movie@time.com (TimetoMovie)"',100,2030,633,-1,NULL,NULL,NULL,NULL,NULL,2322441,NULL,NULL,NULL,0,0,0);
INSERT into RELEASECOMMENT(ID, RELEASEID, TEXT, USERID, CREATEDDATE, HOST) VALUES (1,2,'test comment',0,CURRENT_TIMESTAMP,'localhost');