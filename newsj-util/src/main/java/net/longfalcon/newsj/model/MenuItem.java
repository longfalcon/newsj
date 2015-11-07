package net.longfalcon.newsj.model;

/**
 * User: Sten Martinez
 * Date: 11/6/15
 * Time: 4:15 PM
 */
public class MenuItem {
    private long id;
    private String href;
    private String title;
    private String tooltip;
    private int role;
    private int ordinal;
    private String menuEval;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public String getMenuEval() {
        return menuEval;
    }

    public void setMenuEval(String menuEval) {
        this.menuEval = menuEval;
    }
}
