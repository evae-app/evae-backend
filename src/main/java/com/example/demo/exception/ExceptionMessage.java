package com.example.demo.exception;

import java.time.LocalDateTime;

    public class ExceptionMessage {
        private String date;
        private String path;
        private String message;

        // Constructeur privé pour que seul le builder puisse créer des instances de ExceptionMessage
        private ExceptionMessage() {}

        // Getter pour chaque attribut

        public String getDate() {
            return date;
        }

        public String getPath() {
            return path;
        }


        public String getMessage() {
            return message;
        }

        // Classe Builder pour créer des instances de ExceptionMessage de manière fluide
        public static class Builder {
            private String date;
            private String path;
            private String message;

            public Builder() {
                this.date = LocalDateTime.now().toString();
            }

            public Builder date(String date) {
                this.date = date;
                return this;
            }

            public Builder path(String path) {
                this.path = path;
                return this;
            }


            public Builder message(String message) {
                this.message = message;
                return this;
            }

            public ExceptionMessage build() {
                ExceptionMessage exceptionMessage = new ExceptionMessage();
                exceptionMessage.date = this.date;
                exceptionMessage.path = this.path;
                exceptionMessage.message = this.message;
                return exceptionMessage;
            }
        }
    }