package com.webtechdevelopers.sumit.movieticketbookingapp.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.razorpay.Checkout;
import com.razorpay.ExternalWalletListener;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.webtechdevelopers.sumit.movieticketbookingapp.ActivityMain;
import com.webtechdevelopers.sumit.movieticketbookingapp.R;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.Constants;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Movie;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Show;

import org.json.JSONException;
import org.json.JSONObject;

public class FragmentPayment extends Fragment {
    private Show show;
    private float price;
    public FragmentPayment() {}

    public static FragmentPayment newInstance(Bundle bundle) {
        FragmentPayment fragment = new FragmentPayment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            show= (Show) getArguments().getSerializable("show");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Fresco.initialize(container.getContext());
        return inflater.inflate(R.layout.fragment_payment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView calculationText = view.findViewById(R.id.calculationText);
        TextView moviePaymentlName=view.findViewById(R.id.moviePaymentlName);
        Button payButton = view.findViewById(R.id.payButton);
        Movie movie=show.getMovie();
        SimpleDraweeView moviePaymentPoster=view.findViewById(R.id.moviePaymentPoster);
        moviePaymentPoster.setImageURI(Uri.parse(Constants.IMAGE_URL+movie.getPoster_path()));

        SimpleDraweeView moviePaymentBackground=view.findViewById(R.id.moviePaymentBackground);
        Uri backgroundUri = Uri.parse(Constants.IMAGE_URL+movie.getBackdrop_path());
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(backgroundUri)
                .setPostprocessor(new IterativeBoxBlurPostProcessor(20))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(moviePaymentBackground.getController())
                .build();
        moviePaymentBackground.setController(controller);

        String movieTitle=""+movie.getTitle();
        moviePaymentlName.setText(movieTitle);

        price=show.getSeatCount()*show.getSeats().get(0).getPrice();
        String calculation="Venue: "+show.getVenue()
                +"\nTotal Seats: "+show.getSeatCount()
                +"\nSeat rate: "+ show.getSeats().get(0).getPrice()+" INR"
                +"\nTotal: "+price+" INR";
        calculationText.setText(calculation);
        String payButtonText="Pay "+price+"INR";
        payButton.setText(payButtonText);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startPayment(view);
            }
        });
    }

    private void startPayment(View view){
        Checkout checkout=new Checkout();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("name","Movie Ticket Booking App");
            jsonObject.put("description","Ticket count");
            jsonObject.put("currency","INR");
            jsonObject.put("amount",""+(price*100));

            SharedPreferences sharedPreferences=view.getContext().getSharedPreferences(Constants.LOGIN_PREF,Context.MODE_PRIVATE);
            JSONObject preFill = new JSONObject();
            preFill.put("email", ""+sharedPreferences.getString(Constants.EMAIL,""));
            preFill.put("contact", "");
            jsonObject.put("prefill", preFill);

            checkout.open(getActivity(),jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ((ActivityMain)getActivity()).setShow(show);

        Checkout.handleActivityResult(getActivity(), 0, 0, null, (PaymentResultWithDataListener) getActivity(), new ExternalWalletListener() {
            @Override
            public void onExternalWalletSelected(String s, PaymentData paymentData) {

            }
        });
    }
}
