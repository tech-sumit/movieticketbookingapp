package com.webtechdevelopers.sumit.movieticketbookingapp.framework;

import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Seat;

public interface OnSeatClickActionListener {
    void onSeatSelected(Seat seat);
    void onSeatDeselected(Seat seat);
}
