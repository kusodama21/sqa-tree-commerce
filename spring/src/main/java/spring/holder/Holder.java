package spring.holder;

public abstract class Holder<T> {
    protected T central;

    public void setCentral(T t) {
        this.central = t;
    }

    public T getCentral() {
        return central;
    }
}
