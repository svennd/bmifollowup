package Resource;

/*
 * trace    : dataset, unknown
 */
public class FloatHelper
{
   
    // bron : http://www.java-forums.org/advanced-java/4130-rounding-double-two-decimal-places.html
    /*
     *  name    : round2
     *  use     : will round floats 2 digits behind . (ex 3.1415 -> 3.14)
     *  trace   : dataset, unknown
     */
    public float round2(float num) 
    {
        float result = num * 100;
        result = Math.round(result);
        result = result / 100;
        return result;
    }
    
}
