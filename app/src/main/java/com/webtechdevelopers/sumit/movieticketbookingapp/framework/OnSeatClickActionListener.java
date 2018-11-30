package com.webtechdevelopers.sumit.movieticketbookingapp.framework;

import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Seat;

public interface OnSeatClickActionListener {
    public void onSeatSelected(Seat seat);
    public void onSeatDeselected(Seat seat);
}
