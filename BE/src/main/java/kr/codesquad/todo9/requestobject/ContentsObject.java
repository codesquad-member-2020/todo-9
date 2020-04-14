package kr.codesquad.todo9.requestobject;

import javax.validation.constraints.Size;

public class ContentsObject {

    @Size(max = 500)
    private String contents;

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
