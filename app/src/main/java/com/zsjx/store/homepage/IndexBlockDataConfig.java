package com.zsjx.store.homepage;

import com.zjsx.blocklayout.config.DefaultBlockDataConfig;

import java.io.Serializable;

public class IndexBlockDataConfig extends DefaultBlockDataConfig implements Serializable {
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
        if (o instanceof IndexBlockDataConfig) {
            if (version != null) {
                return version.equals(((IndexBlockDataConfig) o).getVersion());
            }
        }
        return super.equals(o);
    }

    @Override
    public String toString() {
        return "IndexBlockDataConfig{" +
                "version='" + version + '\'' +
                ", nextPage='" + nextPage + '\'' +
                '}';
    }
}
