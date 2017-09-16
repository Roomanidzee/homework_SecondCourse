package ru.itis.java_enterprise_course.romanov_andrey.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class Human {

    private Long id;
    private String name;
    private String surname;

    public Human(String name, String surname){

        this.name = name;
        this.surname = surname;

    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }

        Human human = (Human) o;

        if (!this.getId().equals(human.getId())) return false;
        if (!this.getName().equals(human.getName())) return false;
        return this.getSurname().equals(human.getSurname());

    }

    @Override
    public int hashCode() {

        int result = this.getId().hashCode();
        result = 31 * result + this.getName().hashCode();
        result = 31 * result + this.getSurname().hashCode();

        return result;
    }

    @Override
    public String toString(){

        StringBuilder sb = new StringBuilder();

        sb.append("Human{")
          .append("id = ").append(this.getId())
          .append(", name = ").append(this.getName())
          .append(", surname = ").append(this.getSurname())
          .append("}");

        return sb.toString();

    }
}