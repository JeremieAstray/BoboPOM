
package boboPOM.code.anime;

import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.animation.TranslateTransition;
import javafx.util.Duration;


/**
 *
 * @author yorlbgy
 */
public class Transitions {

    public static TranslateTransition newTt(Duration duration, double distanceX, double distanceY) {
        TranslateTransition tt;
        tt = new TranslateTransition(duration);
        tt.setAutoReverse(false);
        tt.setCycleCount(1);
        tt.setByX(distanceX);
        tt.setByY(distanceY);
//        tt.setInterpolator(Interpolator.LINEAR);
        return tt;
    }

    public static ScaleTransition newSt(Duration duration, double scale, boolean autoReverse) {
        ScaleTransition st;
        st = new ScaleTransition(duration);
        st.setCycleCount(Transition.INDEFINITE);
        st.setAutoReverse(autoReverse);
        st.setFromX(0.7);
        st.setFromY(0.7);
        st.setByX(scale);
        st.setByY(scale);
        return st;
    }
   
}