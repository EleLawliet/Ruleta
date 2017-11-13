package ec.com.innovasystem.comandato.Ruleta;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

import ec.com.innovasystem.comandato.Entidades.SectoresRuleta;
import ec.com.innovasystem.comandato.R;


public class RouletteView extends SurfaceView implements SurfaceHolder.Callback {

    private static final int MIN_FORCE = 100;
    private static final int FRAME = 5;//linea del circulo



    private final Paint mPaint = new Paint();
    private final RectF mRectF = new RectF();
    private final Path mPath = new Path();
    private int mStroke;
    private int mCenterY;
    private int mCenterX;
    private int mRadius;
    public int  mSectors;
    private float mSweepAngle;
    private int mAngleOffset = 0;
    private int mLastPointAngle;
    private Float desfase;
    private AccionesRuleta accionesruleta = null;

    private ArrayList<Coordinate> mPoints = new ArrayList<Coordinate>();
    private ArrayList<Coordinate> puntostexto = new ArrayList<Coordinate>();
    public ArrayList<Integer> mSectorColors= new ArrayList<>();
    public ArrayList<SectoresRuleta> opciones= new ArrayList<>();


    private SurfaceHolder mSurfaceHolder;
    Bitmap centroruleta, selector; int reducirruleta;
    private Context mContext;
    private int mAngleDiff = 0;
    private MediaPlayer mediaPlayer;
    //private String[] mAnswers;
    private Cursor mSectorsCursor;
    //private SectorsDbAdapter mDbHelper;
    private int colorfondo = Color.WHITE;
    private boolean seguirmovimientomanual = true;
    private int opcionDetener=-1;
    public RouletteView(Context context) {
        super(context);
        mContext = context;
        initRoulette();
    }

    public RouletteView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initRoulette();
    }

    public RouletteView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initRoulette();
    }

    private void initRoulette() {
        /*Inicializacion de los colores de la ruleta*/
        mSurfaceHolder = getHolder();
        mSurfaceHolder.addCallback(this);
        mediaPlayer = MediaPlayer.create(mContext, R.raw.beat);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        centroruleta = BitmapFactory.decodeResource(getResources(), R.drawable.centroruleta, options);
        centroruleta=Bitmap.createScaledBitmap(centroruleta, ((int) convertDpToPixel(70, getContext())),  ((int) convertDpToPixel(70, getContext())), false);
        //centroruleta=Bitmap.createScaledBitmap(centroruleta,130,130,false);
        reducirruleta = (centroruleta.getWidth()/2);
        selector = BitmapFactory.decodeResource(getResources(), R.mipmap.flecha7,options);
        this.setZOrderOnTop(true);
        mSurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);

    }

    boolean detener = false;
    public void detenerRuleta(){
        detener = true;
    }

    public float convertDpToPixel(float dp, Context context)
    {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return px;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mCenterX = this.getWidth() / 2;
        mCenterY = this.getHeight() / 2;
        mRadius = Math.min(mCenterX, mCenterY)-10;
        int rectRad = mRadius - FRAME;
        mRectF.set(mCenterX - rectRad, mCenterY - rectRad, mCenterX + rectRad,
                mCenterY + rectRad);
        mStroke = 2;
        mPaint.setStrokeWidth(mStroke);
        mPaint.setAntiAlias(true);
        updatePoints(0);
    }

    public void updateSectors() {//update sectors
        if(360%opciones.size()!=0) {
            int value = (360 / opciones.size());// el valor resultante es el valor entero que se le va a dar a cada sector
            float value2=value*opciones.size();// como la cantidad de sectores no es divisible para 360 lo que hacemos es multiplicar para los sectores nos da la cantidad cercana a 360
            float resta=360-value2;//cantidad faltante para llegar a 360
            desfase= Float.valueOf(resta/opciones.size());//el valor fatante para llegar a completar el la ruleta


        }//desfase percibido cuando hay 7 opciones
        else
            desfase = new Float(0.0); //si se utiliza un numero de opciones diferente a 7 habria que calcular si hay o no desfase en el angulo
        mSweepAngle = (360 / opciones.size()) + desfase;
        //360 divvidifo para sectores+ resultado multiplicado para numero de sectores y resultado retsado de 360 y resultado dividido para numero de sectores el resultado se lo suma como desfase causado
        updateColors();
        //updateAnswers();
        updatePoints(0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//
        return true;
    }


    public void moverhastaopcion(int direccion, final int duracion,  int opcion){
        try {
            int currentPointAngle = 0;
            int movimiento = 0;

            if (direccion == 1) { //girar a la derecha
                mLastPointAngle = -60;
                currentPointAngle = 5;
                movimiento = -8;
            }
            if (direccion == -1) { //girar a la izquierda
                mLastPointAngle = 60;
                currentPointAngle = -5;
                movimiento = +16;
            }

          /*  new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(1000 * duracion);
                    } catch(InterruptedException e) {}
                    RouletteView.this.seguirmovimientomanual   = false;
                }
            }).start();*/
            while (true) {
                //Log.i("angulo actual","angulo es "+currentPointAngle);
                currentPointAngle = currentPointAngle + movimiento;
                //mAngleDiff = currentPointAngle - mLastPointAngle;
                updatePoints(mLastPointAngle - currentPointAngle);
                repaint();
                mLastPointAngle = currentPointAngle;
               /* if(mLastPointAngle>=80 && mLastPointAngle<=100 )
                {
                    Log.i("angulo2","ver angulo setor 1 "+mLastPointAngle+" sector "+getCurrentSector()+" movimiento "+this.seguirmovimientomanual);
                    break;
                }*/
                if (getCurrentSector() == opciones.get(getCurrentSector()).getPos() && getOpcionDetener() == opciones.get(getCurrentSector()).getItem_id() && !this.isSeguirmovimientomanual()) {
                    Log.i("sector", "sector actual " + opciones.get(getCurrentSector()).getCantidad() + " " + opciones.get(getCurrentSector()).getPuntos() + " opciones item " + opciones.get(getCurrentSector()).getItem_id() + " item " + getOpcionDetener());
                    break;
                }
                if (detener)
                    return;

            }

            if (accionesruleta != null) {
                accionesruleta.opcionSeleccionadaxboton(getCurrentSector(), opciones.get(getCurrentSector()).getCantidad() + " moneda(s) de " + opciones.get(getCurrentSector()).getPuntos());
                opciones.clear();
                // Log.i("opciones","opciones tamaño "+opciones.size());
            }
        }catch (Exception e){
            Log.i("error","error ");
        }
    }


    private int getAngle(Coordinate point) {
        return (int) Math.toDegrees(Math.atan2(-point.y, point.x));
    }

    private int getangulo2ptos(Coordinate p1,Coordinate p2) {
        return (int)Math.toDegrees(Math.atan(((p1.y - p2.y)/ (p1.x-p2.x))));
    }

    public void repaint() {
        Canvas c = null;
        try {
            c = mSurfaceHolder.lockCanvas(null);
            synchronized (mSurfaceHolder) {
                doDraw(c);
            }
        } finally {
            if (c != null) {
                mSurfaceHolder.unlockCanvasAndPost(c);
            }
        }
    }

    protected void doDraw(Canvas canvas) {
        // Log.i("doDraw","dibuja circulo");
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);

        mPaint.setColor(Color.WHITE);//color del borde de lla ruleta
        canvas.drawCircle(mCenterX, mCenterY, mRadius + 8, mPaint);//ancho del borde, dibuja el circulo
        // Log.i("circle", "dibuja circulo x " + mCenterX + " y " + mCenterY + " el radio es " + mRadius);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        for (int i = 0; i < opciones.size(); i++) {
            // Draw sector

            mPaint.setColor(mSectorColors.get(i));
            Coordinate myPoint  = mPoints.get(i);

            mPath.reset();
            mPath.moveTo(mCenterX, mCenterY);
            mPath.lineTo(myPoint.x, myPoint.y);
            mPath.addArc(mRectF, i * mSweepAngle + mAngleOffset, mSweepAngle);
            mPath.lineTo(mCenterX, mCenterY);
            mPath.close();
            canvas.drawPath(mPath, mPaint);
        }

        float textSize = mPaint.getTextSize();
        //dimenson del telefono
        mPaint.setTextSize(13 * getResources().getDisplayMetrics().density);
        Coordinate centro =  new Coordinate(mCenterX,mCenterY);
        mPaint.setColor(Color.WHITE);
        for (int i = 0; i < opciones.size(); i++) {
            canvas.save();
            Coordinate coordtxt = puntostexto.get(i);
            int angulon = getangulo2ptos(centro, coordtxt );
            if((coordtxt.x <= centro.x && coordtxt.y >= centro.y)||(coordtxt.x <= centro.x && coordtxt.y <= centro.y)) angulon = angulon +180;
            canvas.rotate( angulon ,coordtxt.x, coordtxt.y);
            canvas.drawText(opciones.get(i).getCantidad()+" "+opciones.get(i).getPuntos(),coordtxt.x, coordtxt.y,mPaint);
            canvas.restore();
        }
        mPaint.setTextSize(textSize);
        // Draw lines
        mPaint.setColor(Color.WHITE);
        for (int i = 0; i < opciones.size(); i++) {//dibuja las lineas que dividen cada sector
            Coordinate myPoint = mPoints.get(i);
            canvas.drawLine(mCenterX, mCenterY, myPoint.x, myPoint.y, mPaint);
        }
        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(mStroke * 2);
/*canvas.drawLine(mCenterX + mRadius - FRAME * 2, mCenterY, mCenterX
        + mRadius + FRAME, mCenterY, mPaint);*/

        canvas.drawLine( mCenterX,mCenterY- mRadius - FRAME * 2, mCenterX,mCenterY
                - mRadius + FRAME,  mPaint);
        mPaint.setStrokeWidth(mStroke);
        //dibujar comandato central
        canvas.drawBitmap(centroruleta, mCenterX - reducirruleta, mCenterY - reducirruleta, mPaint);
        canvas.drawBitmap(selector, mCenterX-((int) convertDpToPixel(21, getContext())), mCenterY- mRadius - FRAME*((int) convertDpToPixel(3, getContext())), mPaint);//imagen ,derecha o izquierda, abajo o arriba, paint
        //Log.i("selector","center "+mCenterX);
        //canvas.drawBitmap(selector, mCenterX + mRadius - FRAME , mCenterY - (reducirruleta / 2), mPaint);/*Drawable d = getResources().getDrawable(R.drawable.movie);

    }

    public int getCurrentSector() {
        int angulomove  = mAngleOffset +90;
        while (angulomove < 0) {
            angulomove = angulomove + 360;
        }
        while (angulomove > 360) {
            angulomove = angulomove - 360;
        }
        return opciones.size() - 1 - (int) ( (angulomove ) / mSweepAngle);

    }

    private void updatePoints(int angle) {
        int currentSector = getCurrentSector();
        mAngleOffset = mAngleOffset + angle;
        //Log.i("ruleta2","angulo:"+ angle +" -- angulooffset : "+mAngleOffset);
        while (mAngleOffset < 0) {
            mAngleOffset = mAngleOffset + 360;
        }
        while (mAngleOffset > 360) {
            mAngleOffset = mAngleOffset - 360;
        }
        mPoints.clear();
        puntostexto.clear();
        float newpointX, newpointY;
        float txtx, txty;
        for (int i = 0; i < opciones.size(); i++) {
            newpointX = (float) Math.cos(Math.toRadians(i * mSweepAngle
                    + mAngleOffset))
                    * mRadius + mCenterX;
            newpointY = (float) Math.sin(Math.toRadians(i * mSweepAngle
                    + mAngleOffset))
                    * mRadius + mCenterY;
            mPoints.add(new Coordinate(newpointX, newpointY));

            txtx = (float) Math.cos(Math.toRadians( ( (1+(i*2)) * (mSweepAngle/2) )
                    + mAngleOffset ) )
                    * (mRadius*4/10) + mCenterX;
            txty = (float) Math.sin(Math.toRadians( ( (1+(i*2)) * (mSweepAngle/2) )
                    + mAngleOffset ) )
                    * (mRadius*4/10) + mCenterY;

            puntostexto.add(new Coordinate(txtx, txty));
        }//Log.i("sectorUpdate","sector get "+getCurrentSector()+" current "+currentSector);
        if (getCurrentSector() != currentSector) {
            mediaPlayer.start();
        }
    }


    public void setAccionesruleta(AccionesRuleta accionesruleta) {
        this.accionesruleta = accionesruleta;
    }

    public boolean isSeguirmovimientomanual() {
        return seguirmovimientomanual;
    }

    public void setSeguirmovimientomanual(boolean seguirmovimientomanual) {
        this.seguirmovimientomanual = seguirmovimientomanual;
    }

    public int getOpcionDetener() {
        return opcionDetener;
    }

    public void setOpcionDetener(int opcionDetener) {
        if(opcionDetener >=0){
            new Thread(new Runnable() {
                public void run() {
                    try {
                        Thread.sleep(1000 * 5);
                    } catch(InterruptedException e) {}
                    RouletteView.this.seguirmovimientomanual   = false;
                }
            }).start();
        }
        this.opcionDetener = opcionDetener;
    }

    private class Coordinate {
        public float x;
        public float y;

        public Coordinate(float newX, float newY) {
            x = newX;
            y = newY;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        repaint();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private void updateColors() {/*
        if(mSectorColors== null)
            mSectorColors = new ArrayList<>();
        else
            mSectorColors.clear();
        mSectorColors.add(0xFF913188);
        mSectorColors.add(0xFF675CA1);
        mSectorColors.add(0xFFB7D06B);
        mSectorColors.add(0xFFEE7009);
        mSectorColors.add(0xFF913188);
        mSectorColors.add(0xFF675CA1);
        mSectorColors.add(0xFFB7D06B);
        mSectorColors.add(0xFFEE7009);*/
    }


}
