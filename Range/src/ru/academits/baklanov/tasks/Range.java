package ru.academits.baklanov.tasks;

public class Range {
    private double from;
    private double to;

    public Range(double from, double to) {
        this.from = from;
        this.to = to;
    }

    public double getFrom() {
        return from;
    }

    public double getTo() {
        return to;
    }

    public void setFrom(double from) {
        this.from = from;
    }

    public void setTo(double to) {
        this.to = to;
    }

    public double getLength() {
        return this.to - this.from;
    }

    public boolean isInside(double x) {
        return (this.from <= x && x <= this.to);
    }

    public Range getIntersection(Range range) {
        double newFrom = Math.max(this.from, range.from);
        double newTo = Math.min(this.to, range.to);

        if (newFrom <= newTo) {
            return new Range(newFrom, newTo);
        } else {
            return null;
        }
    }

    public Range[] getUnion(Range range) {
        if (range.from <= this.to && this.from <= range.to) {
            double newFrom = Math.min(this.from, range.from);
            double newTo = Math.max(this.to, range.to);

            return new Range[]{new Range(newFrom, newTo)};
        } else if (this.from < range.from) {
            return new Range[]{new Range(this.from, this.to), new Range(range.from, range.to)};
        } else {
            return new Range[]{new Range(range.from, range.to), new Range(this.from, this.to)};
        }
    }

    public Range[] minus(Range range) {
        if (this.from >= range.to || range.from >= this.to) {
            return new Range[]{new Range(this.from, this.to)};
        } else if (this.from >= range.from && this.to <= range.to) {
            return new Range[]{};
        } else if (this.from < range.from && this.to > range.to) {
            return new Range[]{new Range(this.from, range.from), new Range(range.to, this.to)};
        } else if (this.from < range.from) {
            return new Range[]{new Range(this.from, range.from)};
        } else {
            return new Range[]{new Range(range.to, this.to)};
        }
    }
}