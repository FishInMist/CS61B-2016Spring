import synthesizer.GuitarString;
public class GuitarHero {
    private static final String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";
    public static void main(String[] args) {
        GuitarString[] piano = new GuitarString[37];
        for (int i = 0; i < 37; i++) {
            piano[i] = new GuitarString(440 * Math.pow(2, (i - 24) / 12.0));
        }
        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (keyboard.indexOf(key) != -1) {
                    GuitarString keyplayed = piano[keyboard.indexOf(key)];
                    keyplayed.pluck();
                }
            }

        /* compute the superposition of samples */
            double sample = 0.0;
            for (GuitarString key : piano) {
                sample = sample + key.sample();
            }

        /* play the sample on standard audio */
            StdAudio.play(sample);

        /* advance the simulation of each guitar string by one step */
            for (GuitarString key : piano) {
                key.tic();
            }
        }
    }
}
