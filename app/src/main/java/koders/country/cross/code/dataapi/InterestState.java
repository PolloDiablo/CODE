package koders.country.cross.code.dataapi;

/**
 * Created by Wallace on 22/02/2015.
 */
public class InterestState {

        private String name;
        private boolean selected;

        public InterestState(String name) {
            this.name = name;
            selected = false;
        }

        public String getName() {
            return name;
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

}
