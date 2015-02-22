package koders.country.cross.code.dataapi;

import koders.country.cross.code.dataapi.datatypes.Interest;

/**
 * Created by Wallace on 22/02/2015.
 */
public class InterestState {

        private String name;
        private Interest theInterest;
        private boolean selected;

        public InterestState(String name, Interest inInterest ) {
            this.name = name;
            selected = false;
            theInterest = inInterest;
        }

        public String getName() {
            return name;
        }
        public String getDisplayName() {
            return theInterest.getDisplayName();
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean selected) {
            this.selected = selected;
        }

}
