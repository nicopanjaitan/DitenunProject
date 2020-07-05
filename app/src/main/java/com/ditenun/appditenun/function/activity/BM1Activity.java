package com.ditenun.appditenun.function.activity;

import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.DragEvent;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.ditenun.appditenun.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BM1Activity extends AppCompatActivity {



    private ImageView ivImage1,imgbg, ivImage2,cancelimg,undoimg,redoimg,saveimg,garbageimg;
    Context context;
    ImageView img1,img2,img3,img4,img5,img6,img7,img8,img9,img10,img11,img12,img13,img16,img17,img18,img19,img20,img21,img22,img23,img24,img25,img26,img27,img28,img31,img32,img33,img34,img35,img36,img37,img38,img39,img40,img41,img42,img43,img46,img47, img48, img49, img50, img51, img52, img53, img54, img55, img56, img57, img58, img61, img62, img63, img64, img65, img66, img67, img68, img69, img70, img71, img72, img73;

    Button btnLoadImage;
    TextView textSource;
    ImageView imageMotif;
    SeekBar hueBar, satBar, valBar;
    TextView hueText, satText, valText;

    final int RQS_IMAGE1 = 1;

    Uri source;
    Bitmap bitmapMaster;
    Canvas canvasMaster;
    RelativeLayout containerBM,containercenterBM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bm1);
        initial();
        touchAndDrag();



        //oncreate
        garbageimg.setVisibility(View.GONE);

        //button change color background
        findViewById(R.id.btnBlack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                containercenterBM.setBackgroundResource(R.color.black);
            }
        });

        findViewById(R.id.btnDarkRed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                containercenterBM.setBackgroundResource(R.color.BloodkRed);
            }
        });

        //Onclick Listener
        findViewById(R.id.motif_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, RQS_IMAGE1);
            }
        });
        hueBar = (SeekBar) findViewById(R.id.huebar);
        satBar = (SeekBar) findViewById(R.id.satbar);
        valBar = (SeekBar) findViewById(R.id.valbar);

        //Saat belum ada foto
        hueBar.setVisibility(View.GONE);
        satBar.setVisibility(View.GONE);
        valBar.setVisibility(View.GONE);

        hueBar.setOnSeekBarChangeListener(seekBarChangeListener);
        satBar.setOnSeekBarChangeListener(seekBarChangeListener);
        valBar.setOnSeekBarChangeListener(seekBarChangeListener);

        //Hue end


        cancelimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // exit function

                AlertDialog.Builder builder = new AlertDialog.Builder(BM1Activity.this);
                builder.setTitle("Batalkan Desain");
                builder.setMessage("Anda yakin ingin membatalkan desain anda?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                finish();
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        undoimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // undo function


            }

        });

        redoimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //redo function


            }
        });

        saveimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(BM1Activity.this);
                builder.setTitle("Simpan Desain");
                //builder.setIcon(R.drawable.ic_cancel);
                builder.setMessage("Anda ingin menyimpan gambar?")
                        .setCancelable(false)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                               containerBM.setDrawingCacheEnabled(true);
                               Bitmap myBitmap = containerBM.getDrawingCache();
                                startSave(myBitmap);

                                //startSave();
                                startActivity(new Intent(BM1Activity.this, HomeActivity.class));
                                finish();
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

    }


    public void startSave(Bitmap image){
        FileOutputStream fout = null;
        File filepath = Environment.getExternalStorageDirectory();

        File dirfile = new File(filepath.getAbsoluteFile()+"/DE disimpan/");
        dirfile.mkdirs();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmsshhmmss");
        String date = simpleDateFormat.format(new Date());
        String name = "Img"+date+".jpg";
//        String file_name = filepath.getAbsolutePath()+"/"+name;
        File newFile = new File(dirfile.getAbsolutePath()+"/"+name);
        try{
            fout = new FileOutputStream(newFile);

            //Bitmap bitmap = viewToBitmap(imgbg,imgbg.getWidth(),imgbg.getHeight());
            image.compress(Bitmap.CompressFormat.JPEG,100,fout);
            Toast.makeText(this, "Gambar telah disimpan", Toast.LENGTH_SHORT).show();
            fout.flush();
            fout.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        refreshGallery(newFile);
    }


    // Untuk merefresh gallery setelah gambar disimpan
    public void refreshGallery(File file){
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        sendBroadcast(intent);
    }


    // Convert seluruh gambar ke bitmap
    public static Bitmap viewToBitmap(View view, int width, int height){
        Bitmap bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public void touchAndDrag(){
        ivImage2.setOnTouchListener(new ChoiceTouchListener()); ivImage2.setOnDragListener(new ChoiceDragListener());
        img1.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img1.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img2.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img2.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img3.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img3.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img4.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img4.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img5.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img5.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img6.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img6.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img7.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img7.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img8.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img8.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img9.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img9.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img10.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img10.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img11.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img11.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img12.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img12.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img13.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img13.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img16.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img16.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img17.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img17.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img18.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img18.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img19.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img19.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img20.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img20.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img21.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img21.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img22.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img22.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img23.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img23.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img24.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img24.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img25.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img25.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img26.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img26.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img27.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img27.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img28.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img28.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img31.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img31.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img32.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img32.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img33.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img33.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img34.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img34.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img35.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img35.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img36.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img36.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img37.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img37.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img38.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img38.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img39.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img39.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img40.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img40.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img41.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img41.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img42.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img42.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img43.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img43.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img46.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img46.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img47.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img47.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img48.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img48.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img49.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img49.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img50.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img50.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img51.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img51.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img52.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img52.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img53.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img53.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img54.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img54.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img55.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img55.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img56.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img56.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img57.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img57.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img58.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img58.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img61.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img61.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img62.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img62.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img63.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img63.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img64.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img64.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img65.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img65.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img66.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img66.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img67.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img67.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img68.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img68.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img69.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img69.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img70.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img70.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img71.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img71.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img72.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img72.setOnDragListener(new BM1Activity.ChoiceDragListener());
        img73.setOnTouchListener(new BM1Activity.ChoiceTouchListener());img73.setOnDragListener(new BM1Activity.ChoiceDragListener());
        garbageimg.setOnTouchListener(new BM1Activity.ChoiceTouchListener());garbageimg.setOnDragListener(new BM1Activity.ChoiceDragListener());
    }

    private void initial() {

        imgbg =findViewById(R.id.img_bg);
        ivImage2 = findViewById(R.id.iv_image2);
        garbageimg = findViewById(R.id.garbage_imgview);
        //ivImage3 = findViewById(R.id.iv_image3);
        img1 = findViewById(R.id.imageView1);
        img2 = findViewById(R.id.imageView2);
        img3 = findViewById(R.id.imageView3);
        img4 = findViewById(R.id.imageView4);
        img5 = findViewById(R.id.imageView5);
        img6 = findViewById(R.id.imageView6);
        img7 = findViewById(R.id.imageView7);
        img8 = findViewById(R.id.imageView8);
        img9 = findViewById(R.id.imageView9);
        img10 = findViewById(R.id.imageView10);
        img11 = findViewById(R.id.imageView11);
        img12 = findViewById(R.id.imageView12);
        img13 = findViewById(R.id.imageView13);
        img16 = findViewById(R.id.imageView16);
        img17 = findViewById(R.id.imageView17);
        img18 = findViewById(R.id.imageView18);
        img19 = findViewById(R.id.imageView19);
        img20 = findViewById(R.id.imageView20);
        img21 = findViewById(R.id.imageView21);
        img22 = findViewById(R.id.imageView22);
        img23 = findViewById(R.id.imageView23);
        img24 = findViewById(R.id.imageView24);
        img25 = findViewById(R.id.imageView25);
        img26 = findViewById(R.id.imageView26);
        img27 = findViewById(R.id.imageView27);
        img28 = findViewById(R.id.imageView28);
        img31 = findViewById(R.id.imageView31);
        img32 = findViewById(R.id.imageView32);
        img33 = findViewById(R.id.imageView33);
        img34 = findViewById(R.id.imageView34);
        img35 = findViewById(R.id.imageView35);
        img36 = findViewById(R.id.imageView36);
        img37 = findViewById(R.id.imageView37);
        img38 = findViewById(R.id.imageView38);
        img39 = findViewById(R.id.imageView39);
        img40 = findViewById(R.id.imageView40);
        img41 = findViewById(R.id.imageView41);
        img42 = findViewById(R.id.imageView42);
        img43 = findViewById(R.id.imageView43);
        img46 = findViewById(R.id.imageView46);
        img47 = findViewById(R.id.imageView47);
        img48 = findViewById(R.id.imageView48);
        img49 = findViewById(R.id.imageView49);
        img50 = findViewById(R.id.imageView50);
        img51 = findViewById(R.id.imageView51);
        img52 = findViewById(R.id.imageView52);
        img53 = findViewById(R.id.imageView53);
        img54 = findViewById(R.id.imageView54);
        img55 = findViewById(R.id.imageView55);
        img56 = findViewById(R.id.imageView56);
        img57 = findViewById(R.id.imageView57);
        img58 = findViewById(R.id.imageView58);
        img61 = findViewById(R.id.imageView61);
        img62 = findViewById(R.id.imageView62);
        img63 = findViewById(R.id.imageView63);
        img64 = findViewById(R.id.imageView64);
        img65 = findViewById(R.id.imageView65);
        img66 = findViewById(R.id.imageView66);
        img67 = findViewById(R.id.imageView67);
        img68 = findViewById(R.id.imageView68);
        img69 = findViewById(R.id.imageView69);
        img70 = findViewById(R.id.imageView70);
        img71 = findViewById(R.id.imageView71);
        img72 = findViewById(R.id.imageView72);
        img73 = findViewById(R.id.imageView73);

        cancelimg = findViewById(R.id.cancel_imgview);
        undoimg = findViewById(R.id.undo_imgview);
        redoimg = findViewById(R.id.redo_imgview);
        saveimg = findViewById(R.id.save_imgview);

        //container background

        containerBM = findViewById(R.id.layoutcontainerBM);
        containercenterBM = findViewById(R.id.layoutcontainercenter);

    }

    private class ChoiceDragListener implements View.OnDragListener{

        @Override
        public boolean onDrag(View view, DragEvent dragEvent) {

            switch (dragEvent.getAction()){
                case DragEvent.ACTION_DRAG_STARTED:
                    cancelimg.setVisibility(View.INVISIBLE);
                    redoimg.setVisibility(View.INVISIBLE);
                    undoimg.setVisibility(View.INVISIBLE);
                    saveimg.setVisibility(View.INVISIBLE);
                    findViewById(R.id.motif_image).setVisibility(View.INVISIBLE);
                    ivImage2.setVisibility(View.INVISIBLE);
                    garbageimg.setVisibility(View.VISIBLE);
                    findViewById(R.id.seekcontainer).setVisibility(View.INVISIBLE);
                    findViewById(R.id.bgColor).setVisibility(View.INVISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    ImageView v = (ImageView) dragEvent.getLocalState();
                    if(view == garbageimg){
                        ((ImageView)v).setImageDrawable(null);
                    }
                    else {
                        ((ImageView) view).setImageDrawable(v.getDrawable());
                        ((ImageView)view).setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                        //((ImageView)v).setImageDrawable(null);
                    }
                    findViewById(R.id.seekcontainer).setVisibility(View.VISIBLE);
                    break;
                case DragEvent.ACTION_DRAG_ENDED:

                    view.post(new Runnable() {
                        @Override
                        public void run() {
                            ImageView v = (ImageView) dragEvent.getLocalState();
                            cancelimg.setVisibility(View.VISIBLE);
                            redoimg.setVisibility(View.VISIBLE);
                            undoimg.setVisibility(View.VISIBLE);
                            saveimg.setVisibility(View.VISIBLE);
                            findViewById(R.id.motif_image).setVisibility(View.VISIBLE);
                            ivImage2.setVisibility(View.VISIBLE);
                            garbageimg.setVisibility(View.INVISIBLE);
                            findViewById(R.id.bgColor).setVisibility(View.VISIBLE);
                            findViewById(R.id.seekcontainer).setVisibility(View.VISIBLE);
                            hideSystemUI();
                        }
                    });

                    break;
            }
            return true;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RQS_IMAGE1:
                    source = data.getData();

                    try {
                        bitmapMaster = BitmapFactory.decodeStream(getContentResolver().openInputStream(source));


                        // Reset HSV value
                        hueBar.setVisibility(View.VISIBLE);
                        satBar.setVisibility(View.VISIBLE);
                        valBar.setVisibility(View.VISIBLE);

                        hueBar.setProgress(256);
                        satBar.setProgress(256);
                        valBar.setProgress(256);

                        loadBitmapHSV();


                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }



                    break;
            }
        }
    }

    SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {

        @Override
        public void onProgressChanged(SeekBar seekBar, int progress,
                                      boolean fromUser) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            loadBitmapHSV();
        }
    };

    private void loadBitmapHSV() {
        if (bitmapMaster != null) {

            int progressHue = hueBar.getProgress() - 256;
            int progressSat = satBar.getProgress() - 256;
            int progressVal = valBar.getProgress() - 256;


            float hue = (float) progressHue * 360 / 256;
            float sat = (float) progressSat / 256;
            float val = (float) progressVal / 256;

            ivImage2.setImageBitmap(updateHSV(bitmapMaster, hue, sat, val));

        }
    }


    private Bitmap updateHSV(Bitmap src, float settingHue, float settingSat,
                             float settingVal) {

        int w = src.getWidth();
        int h = src.getHeight();
        int[] mapSrcColor = new int[w * h];
        int[] mapDestColor = new int[w * h];

        float[] pixelHSV = new float[3];

        src.getPixels(mapSrcColor, 0, w, 0, 0, w, h);

        int index = 0;
        for (int y = 0; y < h; ++y) {
            for (int x = 0; x < w; ++x) {

                // Convert from Color to HSV
                Color.colorToHSV(mapSrcColor[index], pixelHSV);

                // Adjust HSV
                pixelHSV[0] = pixelHSV[0] + settingHue;
                if (pixelHSV[0] < 0.0f) {
                    pixelHSV[0] = 0.0f;
                } else if (pixelHSV[0] > 360.0f) {
                    pixelHSV[0] = 360.0f;
                }

                pixelHSV[1] = pixelHSV[1] + settingSat;
                if (pixelHSV[1] < 0.0f) {
                    pixelHSV[1] = 0.0f;
                } else if (pixelHSV[1] > 1.0f) {
                    pixelHSV[1] = 1.0f;
                }

                pixelHSV[2] = pixelHSV[2] + settingVal;
                if (pixelHSV[2] < 0.0f) {
                    pixelHSV[2] = 0.0f;
                } else if (pixelHSV[2] > 1.0f) {
                    pixelHSV[2] = 1.0f;
                }

                // Convert back from HSV to Color
                mapDestColor[index] = Color.HSVToColor(pixelHSV);

                index++;
            }
        }

        return Bitmap.createBitmap(mapDestColor, w, h, Bitmap.Config.ARGB_8888);

    }




    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
        else
            showSystemUI();
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    // Shows the system bars by removing all the flags
// except for the ones that make the content appear under the system bars.
    private void showSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }


    public final class ChoiceTouchListener implements View.OnTouchListener {


        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            if ((motionEvent.getAction() == motionEvent.ACTION_DOWN) && ((ImageView) view).getDrawable() != null) {
                ClipData clipData = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(clipData, shadowBuilder, view, 0);
                return true;
            } else {
                // Disinilah event change color muncul.
                //Toast.makeText(SadumActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitByBackKey();
            //moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //Saat force back ditekan
    protected void exitByBackKey() {

        AlertDialog alertbox = new AlertDialog.Builder(this)
                .setMessage("Anda ingin keluar dan membatalkan desain?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {

                        finish();
                        //close();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {

                    // do something when the button is clicked
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                })
                .show();
    }

}
