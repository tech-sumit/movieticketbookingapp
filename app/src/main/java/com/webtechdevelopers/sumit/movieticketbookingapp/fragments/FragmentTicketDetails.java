package com.webtechdevelopers.sumit.movieticketbookingapp.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.postprocessors.IterativeBoxBlurPostProcessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;
import com.webtechdevelopers.sumit.movieticketbookingapp.R;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.Constants;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Movie;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Seat;
import com.webtechdevelopers.sumit.movieticketbookingapp.framework.entities.Show;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

import static android.content.Context.WINDOW_SERVICE;

public class FragmentTicketDetails extends Fragment {

    SimpleDraweeView ticketMovieImage;
    SimpleDraweeView ticketBackground;
    TextView ticketPaymentID;
    TextView ticketMovieName;
    TextView ticketSeats;
    TextView ticketLocation;
    TextView ticketTime;
    ImageView ticketQRCode;
    FloatingActionButton ticketDetailSave;

    private String paymentID;
    private String name;
    private String venue;
    private String time;
    private Show show;
    private Bitmap bitmap;
    public FragmentTicketDetails() {

    }

    public static FragmentTicketDetails newInstance(Bundle bundle){
        FragmentTicketDetails fragmentTicketDetails=new FragmentTicketDetails();
        fragmentTicketDetails.setArguments(bundle);
        return fragmentTicketDetails;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            show= (Show) getArguments().getSerializable("show");
            paymentID=getArguments().getString("payment_id");
            name=getArguments().getString("name");
            venue=getArguments().getString("venue");
            time=getArguments().getString("time");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ticket_details, container, false);
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ticketMovieImage=view.findViewById(R.id.ticketDetailMovieImage);
        ticketBackground=view.findViewById(R.id.ticketDetailBackground);
        ticketPaymentID=view.findViewById(R.id.ticketDetailPaymentID);
        ticketMovieName=view.findViewById(R.id.ticketDetailMovieName);
        ticketSeats=view.findViewById(R.id.ticketDetailSeats);

        ticketLocation=view.findViewById(R.id.ticketDetailLocation);
        ticketTime=view.findViewById(R.id.ticketDetailTime);
        ticketQRCode=view.findViewById(R.id.ticketDetailQRCode);
        ticketDetailSave=view.findViewById(R.id.ticketDetailSave);

        final Movie movie=show.getMovie();

        ticketMovieImage.setImageURI(Uri.parse(Constants.IMAGE_URL+movie.getPoster_path()));
        Uri backgroundUri = Uri.parse(Constants.IMAGE_URL+movie.getBackdrop_path());
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(backgroundUri)
                .setPostprocessor(new IterativeBoxBlurPostProcessor(20))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(ticketBackground.getController())
                .build();
        ticketBackground.setController(controller);
        ticketPaymentID.setText(paymentID);
        ticketMovieName.setText(name);
        ArrayList<Seat> seats=show.getSeats();
        String bookedSeats="";
        for(Seat seat: seats){
            bookedSeats+=seat.getSeat_no()+" ";
        }
        String ticketSeatsText="Seats: "+bookedSeats;
        ticketSeats.setText(ticketSeatsText);
        Log.i("MovieDetails","\nData: "+movie.toString());
        ticketLocation.setText(venue);
        ticketTime.setText(time);
        WindowManager manager = (WindowManager) view.getContext().getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;
        //smallerDimension = smallerDimension * 3 / 4;

        String data="Movie name: "+name+"" +
                "\nPayment ID: "+paymentID+
                "\nSeats: "+ticketSeatsText+
                "\nLocation: "+venue+
                "\nTime: "+time;
        QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, smallerDimension);
        try {
            bitmap = qrgEncoder.encodeAsBitmap();
            ticketQRCode.setImageBitmap(bitmap);
        } catch (com.google.zxing.WriterException e) {
            e.printStackTrace();
        }

        ticketDetailSave.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                ticketDetailSave.setVisibility(View.GONE);
                bitmap=screenShot(view);
                Document document = new Document();
                try {
                    String file_name=movie.getTitle()+show.getVenue()+show.getTime()+".png";
                    String path=Environment.getExternalStorageDirectory().toString()+"/"+getString(R.string.app_name);
                    File dest = new File(path);
                    if(!dest.exists()){
                        dest.mkdirs();
                    }
                    File file=new File(dest,file_name);
                    if (file.exists ()) file.delete ();
                    FileOutputStream out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 80, out);
                    out.flush();
                    out.close();

                    PdfWriter.getInstance(document, new FileOutputStream(path+"/"+movie.getTitle()+show.getVenue()+show.getTime()+".pdf"));
                    document.open();
                    Image image = Image.getInstance(path+"/"+file_name);

                    float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                            - document.rightMargin() - 0) / image.getWidth()) * 100;
                    image.scalePercent(scaler);
                    image.setAlignment(Image.ALIGN_TOP|Image.ALIGN_BOTTOM|Image.ALIGN_CENTER|Image.ALIGN_JUSTIFIED_ALL);
                    //image.setAlignment(Image.ALIGN_JUSTIFIED_ALL);
                    document.add(image);
                    document.close();

                    file.delete();
                    ticketDetailSave.setVisibility(View.VISIBLE);

                    Toast.makeText(view.getContext(),"PDF saved",Toast.LENGTH_SHORT).show();
                } catch (DocumentException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Bitmap screenShot(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        //Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.setDrawingCacheEnabled(false);
        //view.draw(canvas);
        return bitmap;
    }
    public static String getAppPath(Context context) {
        File dir = new File(android.os.Environment.getExternalStorageDirectory()
                + File.separator
                + context.getResources().getString(R.string.app_name)
                + File.separator);
        if (!dir.exists()) {
            dir.mkdir();
        }
        return dir.getPath() + File.separator;
    }
}
