package com.example.rick_hp.myapplication;

public class Prediccion {
        private long temperatura;
        private double humedad;
        private String correo,codfamilia;


        public Prediccion() {
        }
        public Prediccion(String correo){

        }

        public Prediccion(String cielo, long temperatura, double humedad)
        {

            this.temperatura = temperatura;
            this.humedad = humedad;
        }


        public String getCorreo(){return correo;}

        public String getCodigo(){return codfamilia;}

        public long getTemperatura() {
            return temperatura;
        }

        public void setTemperatura(long temperatura) {
            this.temperatura = temperatura;
        }

        public double getHumedad() {
            return humedad;
        }

        public void setHumedad(double humedad) {
            this.humedad = humedad;
        }

        @Override
        public String toString() {
            return "Prediccion{" +

                    ", temperatura=" + temperatura +
                    ", humedad=" + humedad +
                    '}';
        }
}

