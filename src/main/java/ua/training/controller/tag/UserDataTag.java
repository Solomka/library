package ua.training.controller.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import ua.training.locale.Message;
import ua.training.locale.MessageLocale;
import ua.training.locale.MessageUtils;
import ua.training.model.entity.User;

@SuppressWarnings("serial")
public class UserDataTag extends TagSupport {
	private User user;

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			pageContext.getOut().write(showUserData());
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		return SKIP_BODY;
	}

	private String showUserData() {
		return new StringBuffer().append(MessageLocale.BUNDLE.getString(Message.LOGGED_IN_AS)).append(user.getEmail())
				.append(MessageUtils.LEFT_PARENTHESIS)
				.append(MessageLocale.BUNDLE.getString(user.getRole().getLocalizedValue()))
				.append(MessageUtils.RIGHT_PARANTHESIS).toString();
	}
}