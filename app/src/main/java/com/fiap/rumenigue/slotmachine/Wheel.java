package com.fiap.rumenigue.slotmachine;

public class Wheel extends Thread {
    interface WheelListener {
        void newImage(int resourceID);
    }

    private static int[] slotsImages = {R.drawable.arbok, R.drawable.beedril, R.drawable.buterfree,
    R.drawable.ninetales, R.drawable.pikachu, R.drawable.raticate};

    public int currentIndex;
    private WheelListener wheelListener;
    private long frameDuration;
    private long startsIn;
    private boolean isStarted;

    public Wheel(WheelListener wheelListener, long frameDuration, long startsIn){
        this.wheelListener = wheelListener;
        this.frameDuration = frameDuration;
        this.startsIn = startsIn;
        currentIndex = 0;
        isStarted = true;
    }

    public void nextImage(){
        currentIndex++;
        if(currentIndex == slotsImages.length){
            currentIndex = 0;
        }
    }

    @Override
    public void run() {
        try {
            Thread.sleep(startsIn);
        } catch (InterruptedException e) {
        }

        while (isStarted) {
            try {
                Thread.sleep(frameDuration);
            } catch (InterruptedException e) {
            }

            nextImage();

            if (wheelListener != null) {
                wheelListener.newImage(slotsImages[currentIndex]);
            }
        }
    }

    public void stopSpinning(){
        isStarted = false;
    }
}