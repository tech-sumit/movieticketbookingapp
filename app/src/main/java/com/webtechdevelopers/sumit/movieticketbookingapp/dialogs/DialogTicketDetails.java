package com.webtechdevelopers.sumit.movieticketbookingapp.dialogs;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.Display;
import android.view.View;
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
import com.itextpdf.text.PageSize;
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

public class DialogTicketDetails extends Dialog {

    private final Bundle bundle;
    private FloatingActionButton ticketDetailSave;

    @Nullable
    private Show show;
    private Bitmap bitmap;
    private final View view;
    @NonNull
    private final Context context;

    public DialogTicketDetails(@NonNull Context context, Bundle bundle) {
        super(context,R.style.AppTheme_NoActionBar_Dark);
        this.context=context;
        this.bundle=bundle;
        view=View.inflate(context,R.layout.layout_ticket_details, null);
        setContentView(view);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        show= (Show) bundle.getSerializable("show");
        String paymentID = bundle.getString("payment_id");
        String name = bundle.getString("name");
        String venue = bundle.getString("venue");
        String time = bundle.getString("time");
        SimpleDraweeView ticketMovieImage = findViewById(R.id.ticketDetailMovieImage);
        SimpleDraweeView ticketBackground = findViewById(R.id.ticketDetailBackground);
        TextView ticketPaymentID = findViewById(R.id.ticketDetailPaymentID);
        TextView ticketMovieName = findViewById(R.id.ticketDetailMovieName);
        TextView ticketSeats = findViewById(R.id.ticketDetailSeats);

        TextView ticketLocation = findViewById(R.id.ticketDetailLocation);
        TextView ticketTime = findViewById(R.id.ticketDetailTime);
        ImageView ticketQRCode = findViewById(R.id.ticketDetailQRCode);
        ticketDetailSave=findViewById(R.id.ticketDetailSave);

        final Movie movie= show != null ? show.getMovie() : null;

        assert movie != null;
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
        StringBuilder bookedSeats= new StringBuilder();
        for(Seat seat: seats){
            bookedSeats.append(seat.getSeat_no()).append(" ");
        }
        String ticketSeatsText="Seats: "+bookedSeats;
        ticketSeats.setText(ticketSeatsText);
        Log.i("MovieDetails","\nData: "+movie.toString());
        ticketLocation.setText(venue);
        ticketTime.setText(time);
        WindowManager manager = (WindowManager) getContext().getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        int smallerDimension = width < height ? width : height;

        String data="Movie name: "+ name +"" +
                "\nPayment ID: "+ paymentID +
                "\nSeats: "+ticketSeatsText+
                "\nLocation: "+ venue +
                "\nTime: "+ time;
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
                    String path=Environment.getExternalStorageDirectory().toString()+"/"+context.getString(R.string.app_name);
                    File dest = new File(path);
                    if(!dest.exists()){
                        dest.mkdirs();
                    }
                    File file=new File(dest,file_name);
                    if (file.exists ()) file.delete ();
                    FileOutputStream out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 50, out);
                    out.flush();
                    out.close();

                    PdfWriter.getInstance(document, new FileOutputStream(path+"/"+movie.getTitle()+show.getVenue()+show.getTime()+".pdf"));
                    document.open();
                    document.setPageSize(PageSize.A4);
                    Image image = Image.getInstance(path+"/"+file_name);

                    float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                            - document.rightMargin() - 0) / image.getWidth()) * 100;

                    image.scalePercent(scaler);

                    image.scaleToFit(PageSize.A4.getWidth(),PageSize.A4.getHeight());
                    image.setAlignment(Image.ALIGN_CENTER);
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


    private Bitmap screenShot(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        //Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        view.setDrawingCacheEnabled(false);
        //view.draw(canvas);
        return bitmap;
    }

}
