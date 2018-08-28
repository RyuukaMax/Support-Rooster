package com.mock.fathi.supportrooster.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Employees implements Serializable {
    @SerializedName("engineers")
    private List<Engineers> engineersList = new ArrayList<>();

    public List<Engineers> getEngineersList() {
        return engineersList;
    }

    public void setEngineers(List<Engineers> engineersList) {
        this.engineersList = engineersList;
    }

    public class Engineers implements Serializable {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;

        public Engineers() {}

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }
}
