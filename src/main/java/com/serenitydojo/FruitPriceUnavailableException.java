package com.serenitydojo;

public class FruitPriceUnavailableException extends RuntimeException{
        public FruitPriceUnavailableException(String message){
            super(message);
        }
        public FruitPriceUnavailableException(String message, Throwable cause){
            super(message, cause);
        }
}
