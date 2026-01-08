CREATE TABLE refresh_token (
  id UUID PRIMARY KEY,
  token VARCHAR(128) NOT NULL UNIQUE,
  user_id UUID NOT NULL,
  expires_at TIMESTAMP NOT NULL,
  revoked BOOLEAN NOT NULL DEFAULT FALSE,
  CONSTRAINT fk_refresh_user FOREIGN KEY (user_id) REFERENCES app_user(id)
);

CREATE INDEX ix_refresh_user ON refresh_token(user_id);
