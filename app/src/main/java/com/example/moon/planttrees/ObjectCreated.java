package com.example.moon.planttrees;

class ObjectCreated {
    String _name;
    int ratio;
    int id_number;
    String unit;

    public ObjectCreated(String _name, int ratio, int id_number,String unit) {
        this._name = _name;
        this.ratio = ratio;
        this.id_number = id_number;
        this.unit = unit;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }

    public int getId_number() {
        return id_number;
    }

    public void setId_number(int id_number) {
        this.id_number = id_number;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
