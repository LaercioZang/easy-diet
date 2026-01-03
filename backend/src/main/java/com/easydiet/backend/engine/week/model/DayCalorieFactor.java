package com.easydiet.backend.engine.week.model;

public enum DayCalorieFactor {
    TRAINING(1.0),
    REST(0.90);

    private final double factor;

    DayCalorieFactor(double factor) {
        this.factor = factor;
    }

    public double apply(int calories) {
        return calories * factor;
    }
}