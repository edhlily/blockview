package com.zjsx.blocklayout.module;

import java.io.Serializable;

public class ModuleJson implements Serializable {
    private String type;
    private String name;
    private String version;
    private String filePath;
    private float margin;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name + "";
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public float getMargin() {
        return margin;
    }

    public void setMargin(float margin) {
        this.margin = margin;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ModuleJson) {
            return getName().equals(((ModuleJson) o).getName());
        }
        return super.equals(o);
    }

    @Override
    public String toString() {
        return "ModuleJson{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                ", filePath='" + filePath + '\'' +
                '}';
    }
}
