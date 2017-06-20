package com.zsjx.store.homepage;

import com.zjsx.blocklayout.config.DefaultBlockConfig;

import java.io.Serializable;

public class IndexBlockConfig extends DefaultBlockConfig implements Serializable {
    private String version;
    private String nextPage;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof IndexBlockConfig) {
            if (version != null) {
                return version.equals(((IndexBlockConfig) o).getVersion());
            }
        }
        return super.equals(o);
    }

    @Override
    public String toString() {
        return "IndexBlockConfig{" +
                "version='" + version + '\'' +
                ", nextPage='" + nextPage + '\'' +
                '}';
    }
}
