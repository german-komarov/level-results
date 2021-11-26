package com.german.levelresults;

public class InfoDto {
    private Integer user_id;
    private Integer level_id;
    private Integer result;

    public InfoDto() {
    }

    public InfoDto(Integer user_id, Integer level_id, Integer result) {
        this.user_id = user_id;
        this.level_id = level_id;
        this.result = result;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getLevel_id() {
        return level_id;
    }

    public void setLevel_id(Integer level_id) {
        this.level_id = level_id;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }
}
