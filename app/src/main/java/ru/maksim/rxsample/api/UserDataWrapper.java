package ru.maksim.rxsample.api;


class UserDataWrapper {

    String data;

    UserDataWrapper(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "user=" + data;
    }
}
