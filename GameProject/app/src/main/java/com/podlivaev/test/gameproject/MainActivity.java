package com.podlivaev.test.gameproject;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayMetrics = MainActivity.this.getResources().getDisplayMetrics();

    //    starView = (GridView) findViewById(R.id.starView);

        backImage = (ImageView) findViewById(R.id.backImage);
        backImage.setScaleType(ImageView.ScaleType.CENTER);
        backImage.setImageResource(R.drawable.folga);

        chocolateTiles = new ChocolateTiles();
        giveStar = new GiveStar();

        rootLayout = (RelativeLayout) findViewById(R.id.rootLayout);

        starList = new LinkedList<ImageView>();
        createViews();

    }


    /**
     * Позиционирование звёздочки в правом верхнем углу. Начинается с анимации перемещения
     * из позиции с плиткой шоколада в необходимую позицию сверху.
     *
     * @param pos номер плитки шоколада. Рассчёт идёт слева направо, сверху вниз.
     *            Всего 24 плитки [0 - 23]
     */
    public void addStar(int pos){

        ImageView i = chocolateTiles.imIndexes[pos];
        Log.d(LOG_TAG,"imageView hash " + i.toString());
        int [] xy = new int[2];
        i.getLocationOnScreen(xy);

        ImageView imageView = new ImageView(this);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        imageView.setLayoutParams(lp);

        imageView.setImageResource(R.drawable.star_000);

        rootLayout.addView(imageView, lp);

        Log.d(LOG_TAG, "find star at " + pos + " coordinates are: " + xy[0] + ":" + xy[1]);


        starList.add(imageView);
        TranslateAnimation anim = new TranslateAnimation(
                Animation.ABSOLUTE, xy[0],
                Animation.ABSOLUTE, pxToDp(800 - 150 *(starList.size())),
                Animation.ABSOLUTE, pxToDp(xy[1]),
                Animation.ABSOLUTE, pxToDp(50));

        anim.setDuration(1000);
        anim.setFillAfter(false);
        imageView.startAnimation(anim);
        rootLayout.postDelayed(new FireStars(), 1000);

    }


    /**
     * Размещение всех 24х плиток шоколада в центре экрана
     */
    public void createViews(){
        int num = 0;
        for(int y = 0 ; y < 6; y++){
            for(int x=0; x < 4; x++) {
                ImageView imageView = new ImageView(this);

                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                lp.addRule(RelativeLayout.ALIGN_PARENT_START);
                lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);

                int choco_size = 125;
        /*         Log.d(LOG_TAG, "set bounds are: " +
                                 (400 + choco_size * (x - 2)) + "  " +
                                 pxToDp(400 + choco_size * (x - 2)) + ":" +
                                 pxToDp(600 + choco_size * (y - 3))
                 ); */
                lp.setMargins(
                        pxToDp(400 + choco_size * (x - 2)),
                        pxToDp(600 + choco_size * (y - 3)),
                        0, 0);

                imageView.setId(imageView.generateViewId());
                imageView.setImageResource(chocolateTiles.topImages[num]);
           //     imageView.setBackgroundResource(chocolateTiles.tileIndexes[num]);
                chocolateTiles.imIndexes[num] = imageView;

                rootLayout.addView(imageView, lp);
                imageView.setOnClickListener(new CListener(num));
                imageView.setSoundEffectsEnabled(false);

                num++;

            }
        }
    }

    /**
     * Размещение всех 24х плиток шоколада за экраном
     */
    public void createViewsMoveable() {
        int num = 0;
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 4; x++) {
                ImageView imageView = new ImageView(this);

                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT);

                lp.addRule(RelativeLayout.ALIGN_PARENT_START);
                lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);

                int choco_size = 125;

                lp.setMargins(
                        pxToDp(400 + choco_size * (x - 2)),
                        pxToDp(600 + choco_size * (y - 3)),
                        0, 0);


                TranslateAnimation ta = new TranslateAnimation(-1000.f, 0.f, 0.f, 0.f);
                ta.setDuration(1000);
                ta.setFillAfter(true);
                imageView.startAnimation(ta);


                imageView.setId(imageView.generateViewId());
                imageView.setImageResource(chocolateTiles.topImages[num]);
                chocolateTiles.imIndexes[num] = imageView;

                rootLayout.addView(imageView, lp);
                imageView.setOnClickListener(new CListener(num));
                imageView.setSoundEffectsEnabled(false);

                num++;

            }
        }

        TranslateAnimation ta = new TranslateAnimation(-1000.f, 0.f, 0.f, 0.f);
        ta.setDuration(1000);
        ta.setFillAfter(true);
        backImage.startAnimation(ta);
    }


    /**
     * Удаление старых плиток с экрана, для построения новых.
     * Перестроение происходит при исчерпании всех возможных кликов по
     * одной шоколадной плитке
     */
    public void removeViews(){
        for (ImageView tile : chocolateTiles.imIndexes) {
            tile.setOnClickListener(null);
            rootLayout.removeView(tile);
        }
        /*
        backImage.clearAnimation();
        for(ImageView view:chocolateTiles.viewsToDelete){
            rootLayout.removeView(view);
        }
        */
        //rootLayout.removeView(backImage);

        giveStar.stageIncrement();

        chocolateTiles.viewsToDelete.clear();

        chocolateTiles = new ChocolateTiles();
        if(starList.size() < 5) {
            createViewsMoveable();
            //createViews();
        }
    }


    /**
     * обработка нажатия на одну плитку
     * Вызывает анимацию съедания кусочка плитки
     */
    class CListener  implements View.OnClickListener{
        public CListener(int id){
            this.id = id;
        }
        @Override
        public void onClick(View v) {
            if(chocolateTiles.doAnimation)
                return;

            Log.d(LOG_TAG, "click(" + chocolateTiles.numClicks + ") = " + id);

            chocolateTiles.numClicks++;

            if(giveStar.nextGive())
                addStarBelow(id);

            chocolateTiles.doAnimation = true;
            ImageView imageView = (ImageView) v;
            imageView.setImageDrawable(chocolateTiles.animationDrawable);

            v.postDelayed(new AfterEat(id),
                    chocolateTiles.animationDrawable.getDuration(0) *
                            chocolateTiles.animationDrawable.getNumberOfFrames());
            imageView.setOnClickListener(null);



        }
        int id;
    }

    /**
     * Решение о выдаче звёздочки заранее определено
     */
    public class GiveStar{
        public GiveStar(){
            next = false;
            rand = new Random();
            currentStage = 0;
            currentClick = 0;

            clickValues = new boolean[3][7];
            for (boolean[] a: clickValues) {
                Arrays.fill(a, false);
            }

            // Первый экран: от одной до трёх звёздочек
            int numberStarsOnTheFirstStage = rand.nextInt(3) + 1;
            starDistribution(0, numberStarsOnTheFirstStage);

            // Второй экран: до четырёх включительно
            int numberStarsOnTheSecondStage = rand.nextInt(2) + 3 - numberStarsOnTheFirstStage;
            starDistribution(1, numberStarsOnTheSecondStage);

            //Третий экран: 5 звёздочек с вероятностью 1 к 4
            // это rand.nextInt(5) / 4 + 4,
            // на первое время чуть больше
            int numberStarsOnTheThirdStage = ( - rand.nextInt(6) / 4 + 1 ) + 4
                    - numberStarsOnTheFirstStage
                    - numberStarsOnTheSecondStage;
            starDistribution(2, numberStarsOnTheThirdStage);

            // Распечатка результатов игры
            Log.d(LOG_TAG, "Game result:");
            for (boolean[] a: clickValues) {
                Arrays.asList(a);
                String s = "[ ";
                for(boolean b:a)
                    s += b ? "X, " : " , ";
                s +="]";
                Log.d(LOG_TAG, s);
            }
        }

        /**
         * Новый клик - вызываем эту функцию
         * Текущий результат хранится в {@link #give()}
         *
         * @return Наличие звёздочки в текущем клике
         */
        public boolean nextGive(){
            next = rand.nextBoolean();
            if(currentStage > 2)
                next = false;
            else
                next = clickValues[currentStage][currentClick];
            currentClick++;
            return next;
        }

        /**
         * Наличие звёздочки в текущем клике, для перехода к следующему клику требуется
         * вызов {@link #nextGive()}
         * @return есть ли звёздочка в текущем цикле
         */
        public boolean give(){
            return next;
        }

        /**
         * Вызов требуется при переходе на новую шоколадку
         */
        public void stageIncrement(){
            currentStage++;
            currentClick = 0;
        }

        private void starDistribution(int stage, int starNumber){
            for (int i = 0; i < starNumber; i++) {
                int n = rand.nextInt(7);
                if(clickValues[stage][n] == true)
                    i--;
                else
                    clickValues[stage][n] = true;

            }
        }

        private boolean [][] clickValues;
        private Random rand;
        private boolean next;
        private int currentStage;
        private int currentClick;
    }

    /**
     * После анимации съедания плитки шоколада возможные действия:
     * Если под плиткой звёздочка - начать анимацию перемещения звёздочки на
     * верхний край экрана
     *
     * Если истекли все возможные клики по данной плитке шоколада - заменить плитку
     * на новую.
     */
    class AfterEat implements Runnable {
        public AfterEat(int id) {
            this.pos = id;
        }

        @Override
        public void run() {
            Log.d(LOG_TAG, "animation ends " + pos);

            chocolateTiles.topImages[pos] = R.drawable.empty;
       //     if (chocolateTiles.tileIndexes[pos] == R.drawable.star_shape)

            if (giveStar.give()) {
                addStar(pos);
            }

            if(chocolateTiles.numClicks >= 7) {
                doAlphaAnim(1.f, 0.f);
            }
            else {
                chocolateTiles.doAnimation = false;

            }

        }
        int pos;
    }

    /**
     * При уничтожении старой плитки шоколада используется анимация альфа канала
     * @param a Начальная видимость плитки. Обычно 1.f
     * @param b Конечная видимость плитки. Обычно 0.f
     */
    public void doAlphaAnim(float a, float b) {

        //   ArrayList<ImageView> arr = new ArrayList<ImageView>(Arrays.asList(chocolateTiles.imIndexes));
        //    arr.add(backImage);

        //распологаем необнаруженные звёздочки
        int numStarsToDraw = 7 - chocolateTiles.viewsToDelete.size() ;
        numStarsToDraw = numStarsToDraw > 0 ? numStarsToDraw : 1;
        Random r = new Random();
        for (int i = 0; i < numStarsToDraw; i++) {
            int n = r.nextInt(chocolateTiles.SIZE);
            if(chocolateTiles.topImages[n] == R.drawable.empty)
                i--;
            else{
                addStarBelow(n);
            }
        }

        for (int i = 0; i < chocolateTiles.imIndexes.length; i++) {
            AlphaAnimation aa = new AlphaAnimation(a, b);
            aa.setDuration(1500);
            aa.setFillAfter(true);

            if (chocolateTiles.topImages[i] != R.drawable.empty) {
                chocolateTiles.imIndexes[i].clearAnimation();
                chocolateTiles.imIndexes[i].startAnimation(aa);
            }
        }

        if(starList.size() < 5)
            rootLayout.postDelayed(new Runnable() {
                @Override
                public void run() {
                    move_out();
                }
            }, 1500);


    }

    /**
     * Данные о плитках шоколада
     */
    class ChocolateTiles{
        public ChocolateTiles() {
       //     tileIndexes = new Integer[SIZE];
            topImages = new Integer[SIZE];
            imIndexes = new ImageView[SIZE];
            viewsToDelete = new LinkedList<>();

            numClicks = 0;

            for (int i = 0; i < SIZE; i++) {
       //         tileIndexes[i] = R.drawable.empty;
                topImages[i] = R.drawable.chocoladka_000;
            }

            animationDrawable = (AnimationDrawable)
                    ContextCompat.getDrawable(MainActivity.this, R.drawable.eating);
            doAnimation = false;

        }

        public Integer[] topImages; /// Контроль по которым кускам щёлкнили, а по каким - нет.
       // public Integer[] tileIndexes; /// Где есть звёздочка, а где нет
        public ImageView[] imIndexes; /// список View кусков плитки шоколада
        public boolean doAnimation; /// Семафор для аннулирования действий пользователя при анимации
        public int numClicks; /// Сколько раз нажали на одну плитку шоколада
        public AnimationDrawable animationDrawable; /// Анимация "Ест". Нужна в одном экземпляре
        public LinkedList<ImageView> viewsToDelete;
        private static final int SIZE = 4*6; /// размер плитки
    }


    /**
     * Анимация переливания звёздочек используется после открытия очередной  под плиткой
     * шоколада.
     */
    class FireStars implements Runnable{
        @Override
        public void run() {

            ImageView iw = starList.get(starList.size() - 1);
            rootLayout.removeView(iw);

            ImageView iww = new ImageView(MainActivity.this);
            iww.setImageResource(R.drawable.star_create);
            starList.set(starList.size() - 1, iww);


            RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_START);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);

            int n = (800 - 150 * starList.size());
            Log.d(LOG_TAG, "abs=" + n +
                    "pxToDp=" + pxToDp(800 - 150 * starList.size())    );


            layoutParams.setMargins(pxToDp(800 - 150 * starList.size()), pxToDp(50), 0, 0);

            starList.get(starList.size() -1).setLayoutParams(layoutParams);
            rootLayout.addView(starList.get(starList.size() - 1), layoutParams);


            int i = 1;
            for(ImageView star:starList){
                AnimationDrawable ad = (AnimationDrawable) star.getDrawable();
                ad.stop();
                ad.start();

                int [] coord = new int[2];
                star.getLocationOnScreen(coord);
                Log.d(LOG_TAG, "bounds are: " + coord[0] + ":" + coord[1]);

            }

            if (starList.size() == 5) {
                showPrize();
            }
        }
    }

    /**
     * Собрали звёздочки,
     * сообщаем о выигрыше,
     * выдаём шоколадку.
     */
    private void showPrize() {

        ContextThemeWrapper newContext = new ContextThemeWrapper(this, R.style.win_frame);

        TextView winView = new TextView(newContext);

        winView.setText(R.string.win_caption);
        winView.setBackgroundResource(R.drawable.win_frame);

        RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        rootLayout.addView(winView, layoutParams);

        for (ImageView tile : chocolateTiles.imIndexes) {
            tile.setOnClickListener(null);
        }
    }


    /**
     * Часть элементов добавляется во время игры.
     * функция добавляет виджет со звёздочкой за кусочном шоколадки
     * @param n номер куска плитки шоколада, за которым будет звёздочка
     */
    public void  addStarBelow(int n) {

        int x = n % 4;
        int y = n / 4;
        int choco_size = 125;

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                choco_size, choco_size);

        lp.addRule(RelativeLayout.ALIGN_PARENT_START);
        lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);

        //lp.addRule(RelativeLayout.BELOW, chocolateTiles.imIndexes[n].getId());

        lp.setMargins(
                pxToDp(400 + choco_size * (x - 2)),
                pxToDp(600 + choco_size * (y - 3)),
                0, 0);

        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER);

        imageView.setImageResource(R.drawable.star_shape);
        chocolateTiles.viewsToDelete.add(imageView);

        rootLayout.addView(imageView, lp);

        ImageView iw = chocolateTiles.imIndexes[n];
        iw.clearAnimation();
        rootLayout.removeView(iw);
        rootLayout.addView(iw);

    }

    /**
     * Анимация перенаправления фольги со звёздочками от предыдущей шоколадки
     * при замене на новую
     */
    private void move_out(){

        for (ImageView view: chocolateTiles.viewsToDelete) {
            TranslateAnimation ta = new TranslateAnimation(0.f,1000.f,0.f ,0.f);
            ta.setDuration(1000);
            ta.setFillAfter(true);
            view.clearAnimation();
            view.startAnimation(ta);
        }

        TranslateAnimation ta = new TranslateAnimation(0.f,1000.f,0.f ,0.f);
        ta.setDuration(1000);
        ta.setFillAfter(true);

        backImage.clearAnimation();
        backImage.startAnimation(ta);

        rootLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                removeViews();
            }
        }, 1000);

    }

    public void onClick(View view){
       // removeViews();
        //showPrize();
        //doAlphaAnim(1.f,0.f);
        move_out();

    }

    /**
     * рассчёты происходят в пикселах, параметры передаются в dp.
     * Так как плотность экрана планшета чуть ниже 160 dpi( ~157).
     * Производим перерасчёт
     * @param px размер в пикселах
     * @return размер в независимых пикселах
     */
    public int pxToDp(int px) {
        return Math.round(px *((float) DisplayMetrics.DENSITY_DEFAULT) / displayMetrics.xdpi);
    }

    GiveStar giveStar;
    List<ImageView> starList;
    ImageView backImage;
    RelativeLayout rootLayout;
    ChocolateTiles chocolateTiles;
    DisplayMetrics displayMetrics;

    private static final String LOG_TAG = "::game_activity";

}
