package lee.minnanoquiz;

public class Words {
    private String word;
    private String pronounce;
    private String meaning;
    private String comment;
    private String RegUid;
    private String RegUserName;
    private String RegYMD;
    private Integer correct;
    private Integer incorrect;

    public Integer getCorrect() {
        return correct;
    }

    public void setCorrect(Integer correct) {
        this.correct = correct;
    }

    public Integer getIncorrect() {
        return incorrect;
    }

    public void setIncorrect(Integer incorrect) {
        this.incorrect = incorrect;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getPronounce() {
        return pronounce;
    }

    public void setPronounce(String pronounce) {
        this.pronounce = pronounce;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRegUid() {
        return RegUid;
    }

    public void setRegUid(String regUid) {
        RegUid = regUid;
    }

    public String getRegUserName() {
        return RegUserName;
    }

    public void setRegUserName(String regUserName) {
        RegUserName = regUserName;
    }

    public String getRegYMD() {
        return RegYMD;
    }

    public void setRegYMD(String regYMD) {
        RegYMD = regYMD;
    }

    public Words() {
    }

    public Words(String word, String pronounce, String meaning, String comment, String regUid, String regUserName, String regYMD, Integer correct, Integer incorrect) {
        this.word = word;
        this.pronounce = pronounce;
        this.meaning = meaning;
        this.comment = comment;
        RegUid = regUid;
        RegUserName = regUserName;
        RegYMD = regYMD;
        this.correct = correct;
        this.incorrect = incorrect;
    }

    @Override
    public String toString() {
        return "Words{" +
                "word='" + word + '\'' +
                ", pronounce='" + pronounce + '\'' +
                ", meaning='" + meaning + '\'' +
                ", comment='" + comment + '\'' +
                ", RegUid='" + RegUid + '\'' +
                ", RegUserName='" + RegUserName + '\'' +
                ", RegYMD='" + RegYMD + '\'' +
                ", correct=" + correct +
                ", incorrect=" + incorrect +
                '}';
    }
}
