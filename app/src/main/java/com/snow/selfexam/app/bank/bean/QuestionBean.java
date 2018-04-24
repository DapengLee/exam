package com.snow.selfexam.app.bank.bean;

import java.util.List;

public class QuestionBean extends BaseBean {


	private String questionId;// 题目ID
	private String description;// 题目描述
	private int questionType;// 题目类型
	private String knowledgePointName; // 知识点名�?
	private String knowledgePointId; // 知识点id的
	private List<QuestionOptionBean> questionOptions; // 选项集合

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		QuestionBean that = (QuestionBean) o;

		if (questionType != that.questionType) return false;
		if (questionId != null ? !questionId.equals(that.questionId) : that.questionId != null)
			return false;
		if (description != null ? !description.equals(that.description) : that.description != null)
			return false;
		if (knowledgePointName != null ? !knowledgePointName.equals(that.knowledgePointName) : that.knowledgePointName != null)
			return false;
		if (knowledgePointId != null ? !knowledgePointId.equals(that.knowledgePointId) : that.knowledgePointId != null)
			return false;
		if (questionOptions != null ? !questionOptions.equals(that.questionOptions) : that.questionOptions != null)
			return false;
		return answer != null ? answer.equals(that.answer) : that.answer == null;
	}

	@Override
	public int hashCode() {
		int result = questionId != null ? questionId.hashCode() : 0;
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + questionType;
		result = 31 * result + (knowledgePointName != null ? knowledgePointName.hashCode() : 0);
		result = 31 * result + (knowledgePointId != null ? knowledgePointId.hashCode() : 0);
		result = 31 * result + (questionOptions != null ? questionOptions.hashCode() : 0);
		result = 31 * result + (answer != null ? answer.hashCode() : 0);
		return result;
	}

	//答案的选择
	private String answer;

	public QuestionBean(String questionId, String description, int questionType, String knowledgePointName, String knowledgePointId, List<QuestionOptionBean> questionOptions, String answer) {
		this.questionId = questionId;
		this.description = description;
		this.questionType = questionType;
		this.knowledgePointName = knowledgePointName;
		this.knowledgePointId = knowledgePointId;
		this.questionOptions = questionOptions;
		this.answer = answer;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public QuestionBean() {
		super();
	}

	public QuestionBean(String questionId, String description,
                        int questionType, String knowledgePointName,
                        String knowledgePointId, List<QuestionOptionBean> questionOptions) {
		super();
		this.questionId = questionId;
		this.description = description;
		this.questionType = questionType;
		this.knowledgePointName = knowledgePointName;
		this.knowledgePointId = knowledgePointId;
		this.questionOptions = questionOptions;
	}

	public String getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String questionId) {
		this.questionId = questionId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getQuestionType() {
		return questionType;
	}

	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}

	public String getKnowledgePointName() {
		return knowledgePointName;
	}

	public void setKnowledgePointName(String knowledgePointName) {
		this.knowledgePointName = knowledgePointName;
	}

	public String getKnowledgePointId() {
		return knowledgePointId;
	}

	public void setKnowledgePointId(String knowledgePointId) {
		this.knowledgePointId = knowledgePointId;
	}

	public List<QuestionOptionBean> getQuestionOptions() {
		return questionOptions;
	}

	public void setQuestionOptions(List<QuestionOptionBean> questionOptions) {
		this.questionOptions = questionOptions;
	}

	@Override
	public String toString() {
		return "QuestionBean [questionId=" + questionId + ", description="
				+ description + ", questionType=" + questionType
				+ ", knowledgePointName=" + knowledgePointName
				+ ", knowledgePointId=" + knowledgePointId
				+ ", questionOptions=" + questionOptions + "]";
	}

}
