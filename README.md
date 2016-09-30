# newsj
NewsJ - Java Newznab Server

DO NOT USE. NON-FUNCTIONAL

## Roadmap
* v0.1 - general porting of code from legacy newznab
* v0.2 - 90% admin UI, front page, cmd line binary and release processing
* v0.3 - full front page and search, rss, newznab api
* v0.8 - feature complete with original newznab
* v1.0 - final release

## Open Questions before 1.0
* regex submission
* ReqId
* move to templates/Tiles?
* Job management for update binaries / update releases

### ToDo list
* refactor "Rage" to "TVData" or similar
* better image hosting/storage
* manage release data integrity when deleting movie/music/game/tv infos
* match nzb directory with legacy (or port/move legacy dir)

### Bugs
* NZB modal duplicate rendering 
* Some release names with dashes are clipped
* Possible NFO File encoding issues with legacy DBs
