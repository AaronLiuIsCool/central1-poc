package com.central1.course1.domain;

import java.util.List;
import java.util.Map;

public class DomainObject {

    public class MetaData {

        private List<ChainError> errors;
        private Map<String, String> suppliedData;
        private long expiresIn;

        public List<ChainError> getErrors() {
            return errors;
        }

        public void setErrors(List<ChainError> errors) {
            this.errors = errors;
        }

        public Map<String, String> getSuppliedData() {
            return suppliedData;
        }

        public void setSuppliedData(Map<String, String> suppliedData) {
            this.suppliedData = suppliedData;
        }

        public long getExpiresIn() {
            return expiresIn;
        }

        public void setExpiresIn(long expiresIn) {
            this.expiresIn = expiresIn;
        }
    }

}
