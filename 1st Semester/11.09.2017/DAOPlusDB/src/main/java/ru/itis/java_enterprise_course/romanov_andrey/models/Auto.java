package ru.itis.java_enterprise_course.romanov_andrey.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Calendar;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Auto {

    private Long id;

    private String type;
    private String title;
    private Calendar dateOfCreation;

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }

        Auto auto = (Auto) o;

        if (!this.getId().equals(auto.getId())) return false;
        if (!this.getType().equals(auto.getType())) return false;
        if (!this.getTitle().equals(auto.getTitle())) return false;
        return this.getDateOfCreation().equals(auto.getDateOfCreation());

    }

    @Override
    public int hashCode() {

        int result = this.getId().hashCode();
        result = 31 * result + this.getTitle().hashCode();
        result = 31 * result + this.getType().hashCode();
        result = 31 * result + this.getDateOfCreation().hashCode();

        return result;
    }

    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();

        sb.append("Auto{")
                .append("id = ").append(this.getId())
                .append(", type = ").append(this.getType())
                .append(", title = ").append(this.getTitle())
                .append(", dateOfCreation = ").append(this.getDateOfCreation())
                .append("}");

        return sb.toString();

    }
}
