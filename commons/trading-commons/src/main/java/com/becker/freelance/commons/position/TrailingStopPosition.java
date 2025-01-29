package com.becker.freelance.commons.position;


import com.becker.freelance.commons.pair.Pair;
import com.becker.freelance.commons.signal.Direction;
import com.becker.freelance.commons.timeseries.TimeSeriesEntry;
import com.becker.freelance.math.Decimal;

public class TrailingStopPosition extends Position {

    public static Position fromDistances(Decimal size, Direction direction, TimeSeriesEntry openPrice, Pair pair,
                                         Decimal stopInPoints, Decimal limitInPoints, Decimal trailingStepSize, Decimal margin){

        Decimal limitLevel = Position.getLimitLevelFromDistance(direction, openPrice, limitInPoints);
        Decimal stopLevel = Position.getStopLevelFromDistance(direction, openPrice, stopInPoints);

        return fromLevels(size, direction, openPrice, pair, stopLevel, limitLevel, trailingStepSize, margin);
    }

    public static Position fromLevels(Decimal size, Direction direction, TimeSeriesEntry openPrice, Pair pair, Decimal stopLevel, Decimal limitLevel, Decimal trailingStepSize, Decimal margin) {
        return new TrailingStopPosition(size, direction, openPrice, pair, stopLevel, limitLevel, trailingStepSize, margin);
    }

    private Decimal trailingStepSize;

    TrailingStopPosition(Decimal size, Direction direction, TimeSeriesEntry openPrice, Pair pair,
                                Decimal stopInPoints, Decimal limitInPoints, Decimal trailingStepSize, Decimal margin) {
        super(size, direction, openPrice, pair, stopInPoints, limitInPoints, PositionType.TRAILING, margin);
        this.trailingStepSize = trailingStepSize;
    }

    @Override
    public void adapt(TimeSeriesEntry currentPrice) {
        // TODO: Implement trailing stop logic
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public Decimal getTrailingStepSize() {
        return trailingStepSize;
    }
}
