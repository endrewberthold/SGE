ALTER TABLE parking_sessions
ADD COLUMN parking_rate_id BIGINT;

ALTER TABLE parking_sessions
ADD CONSTRAINT fk_parking_session_parking_rate
FOREIGN KEY (parking_rate_id)
REFERENCES parking_rates(id);