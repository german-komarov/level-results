package com.german.levelresults;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Level implements Comparable<Level> {
    private int id;
    private AtomicInteger result;

    public Level() {
    }

    public Level(int id) {
        this.id = id;
    }

    public Level(int id, AtomicInteger result) {
        this.id = id;
        this.result = result;
    }

    @Override
    public int compareTo(Level l) {
        return Integer.compare(this.result.get(), l.result.get());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AtomicInteger getResult() {
        return result;
    }

    public void setResult(AtomicInteger result) {
        this.result = result;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Level)) return false;
        Level level = (Level) o;
        return getId() == level.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
