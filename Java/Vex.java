// Vex.java contains the class Vex and all of its supported methods
// including add, subtract, scalarMult, innerMult, norm1, norm2, equals, clone
class Vex {
    
    int x, y, z;
    // Vex constructor 
    public Vex( int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;   
    }
    //Added incase Vex in intialized empty
    public Vex() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }
    
    @Override
    public String toString() {
        return "Vex( "+ x + ", "+ y + ", "+ z + " )";
    }
    // will add two vectors and assign reult to new Vex
    public Vex add( Vex other) {
        int new_x = this.x + other.x,
                new_y = this.y + other.y,
                new_z = this.z + other.z;
        return new Vex( new_x, new_y, new_z);
    }
    // will subtract two vectors and assign result 
    // to new Vex
    public Vex subtract( Vex other) {
        int new_x = this.x - other.x,
                new_y = this.y - other.y,
                new_z = this.z - other.z;
        return new Vex( new_x, new_y, new_z);
        
    }
    // will multiply each point in the vector by the scalar
    public Vex scalarMult (int scale) {
        int new_x = this.x*scale,
                new_y = this.y*scale,
                new_z = this.z*scale;
        return new Vex( new_x, new_y, new_z);
        
    }
    // will multiple two vectors together and add new x,y,z
    public int innerMult( Vex other) {
        int new_x = this.x*other.x,
                new_y = this.y*other.y,
                new_z = this.z*other.z;
        return new_x + new_y + new_z;
    }
    // will add the absolute values of x,y,z
    public int norm1() {
        
        return Math.abs(this.x)+Math.abs(this.y)+Math.abs(this.z);
    }
    // will square and add x,y,z then take the square root 
    public double norm2() {
        
        return Math.sqrt(Math.pow(this.x,2)+Math.pow(this.y,2)+Math.pow(this.z,2));
        
    }
    // checks to vectors for equality
    public boolean equals( Vex other ) {
        boolean vexEquality = true;
        if (this.x != other.x ){
            vexEquality = false;
        }
        else if ( this.y != other.y) {
            vexEquality = false;
        }
        else if ( this.z != other.z) {
            vexEquality = false;
        }
        return vexEquality;
    }
    // returns a vector to the orginal clone 
    @Override
    public Vex clone() {
        Vex vexClone = new Vex(this.x,this.y,this.z);
        return vexClone;
    }
}
