package ra.demo;

public class Catalogdemo {
    private int catalogId;
    private String catalogName;
    private boolean catalogStatus;

    private Catalogdemo catalogdemo;

    public Catalogdemo() {
    }

    public Catalogdemo(int catalogId, String catalogName, boolean catalogStatus, Catalogdemo catalogdemo) {
        this.catalogId = catalogId;
        this.catalogName = catalogName;
        this.catalogStatus = catalogStatus;
        this.catalogdemo = catalogdemo;
    }

    public int getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(int catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public boolean isCatalogStatus() {
        return catalogStatus;
    }

    public void setCatalogStatus(boolean catalogStatus) {
        this.catalogStatus = catalogStatus;
    }

    public Catalogdemo getCatalogdemo() {
        return catalogdemo;
    }

    public void setCatalogdemo(Catalogdemo catalogdemo) {
        this.catalogdemo = catalogdemo;
    }
}
