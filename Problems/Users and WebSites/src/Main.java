class User extends BaseEntity {

    protected String name;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public long getId() {
        return super.id;
    }

    @Override
    public void setId(long id) {
        super.id = id;
    }

    @Override
    public long getVersion() {
        return super.version;
    }

    @Override
    public void setVersion(long version) {
        super.version = version;
    }
}

class Visit extends BaseEntity {

    protected User user;
    protected WebSite site;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public WebSite getSite() {
        return site;
    }

    public void setSite(WebSite site) {
        this.site = site;
    }

    @Override
    public long getId() {
        return super.id;
    }

    @Override
    public void setId(long id) {
        super.id = id;
    }

    @Override
    public long getVersion() {
        return super.version;
    }

    @Override
    public void setVersion(long version) {
        super.version = version;
    }
}

class WebSite extends BaseEntity {

    protected String url;
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public long getId() {
        return super.id;
    }

    @Override
    public void setId(long id) {
        super.id = id;
    }

    @Override
    public long getVersion() {
        return super.version;
    }

    @Override
    public void setVersion(long version) {
        super.version = version;
    }
}

abstract class BaseEntity {
    protected long id;
    protected long version;
    public abstract long getId();
    public abstract void setId(long id);
    public abstract long getVersion();
    public abstract void setVersion(long version);
}