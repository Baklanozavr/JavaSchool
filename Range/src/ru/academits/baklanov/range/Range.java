package ru.academits.baklanov.range;

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
        double newFrom = (this.from > range.getFrom()) ? this.from : range.getFrom();
        double newTo = (this.to < range.getTo()) ? this.to : range.getTo();

        if (newFrom <= newTo) {
            return new Range(newFrom, newTo);
        } else {
            return null;
        }
    }

    public Range[] getUnion(Range range) {
        if (range.getFrom() <= this.to && this.from <= range.getTo()) {
            double newFrom = (this.from < range.getFrom()) ? this.from : range.getFrom();
            double newTo = (this.to > range.getTo()) ? this.to : range.getTo();

            return new Range[] {new Range(newFrom, newTo)};
        } else if (this.from < range.getFrom()) {
            return new Range[] {new Range(this.from, this.to), new Range(range.getFrom(), range.getTo())};
        } else {
            return new Range[] {new Range(range.getFrom(), range.getTo()), new Range(this.from, this.to)};
        }
    }

    public Range[] minus(Range range) {
        if (this.from >= range.getTo() || range.getFrom() >= this.to) {
            return new Range [] {new Range(this.from, this.to)};
        } else if (this.from >= range.getFrom() && this.to <= range.getTo()) {
            return null;
        } else if (this.from < range.getFrom() && this.to > range.getTo()) {
            return new Range[] {new Range(this.from, range.getFrom()), new Range(range.getTo(), this.to)};
        } else  if (this.from < range.getFrom()) {
            return new Range[]{new Range(this.from, range.getFrom())};
        } else {
            return new Range[]{new Range(range.getTo(), this.to)};
        }
    }
}