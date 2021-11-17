public class Corevar {
    Core type;
    Integer value;

    public Corevar() {
    }

    public Corevar(Core type, Integer val) {
        this.type = type;
        this.value = val;
    }

    public void setCorevar(Core type, Integer val) {
        this.type = type;
        this.value = val;
    }

    public void settype(Core type) {
        this.type = type;
    }

    public void setvalue(Integer val) {
        this.value = val;
    }
}
