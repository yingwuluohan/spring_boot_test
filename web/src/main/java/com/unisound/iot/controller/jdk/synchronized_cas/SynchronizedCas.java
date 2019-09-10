package com.unisound.iot.controller.jdk.synchronized_cas;

public class SynchronizedCas {

    private int num =0;

    public void lock(){
        synchronized ( new CasMange() ){
            num++;
        }

    }

    class CasMange{
        private int i =0;

        public int getNum(){
            return i;
        }
    }
















}
