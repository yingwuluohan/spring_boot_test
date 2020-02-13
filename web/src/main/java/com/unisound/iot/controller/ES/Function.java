package com.unisound.iot.controller.ES;

public class Function {
    private String name;
    private int minArgumentCount;
    private int maxArgumentCount;

    public Function(String name, int argumentCount) {
        this(name, argumentCount, argumentCount);
    }

    public Function(String name, int minArgumentCount, int maxArgumentCount) {
        if (minArgumentCount >= 0 && minArgumentCount <= maxArgumentCount) {
            if (name != null && name.length() != 0) {
                this.name = name;
                this.minArgumentCount = minArgumentCount;
                this.maxArgumentCount = maxArgumentCount;
            } else {
                throw new IllegalArgumentException("Invalid function name");
            }
        } else {
            throw new IllegalArgumentException("Invalid argument count");
        }
    }

    public String getName() {
        return this.name;
    }

    public int getMinimumArgumentCount() {
        return this.minArgumentCount;
    }

    public int getMaximumArgumentCount() {
        return this.maxArgumentCount;
    }
}
