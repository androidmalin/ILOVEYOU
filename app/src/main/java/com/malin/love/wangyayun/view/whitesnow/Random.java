package com.malin.love.wangyayun.view.whitesnow;

class Random {
    private static final java.util.Random RANDOM = new java.util.Random();

    Random() {
    }

    public float getRandom(float lower, float upper) {
        float min = Math.min(lower, upper);
        return getRandom(Math.max(lower, upper) - min) + min;
    }

    public float getRandom(float upper) {
        return RANDOM.nextFloat() * upper;
    }

    public int getRandom(int upper) {
        return RANDOM.nextInt(upper);
    }
}
