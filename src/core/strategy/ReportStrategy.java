package core.strategy;

import models.academic.Report;

import java.io.Serializable;
import java.util.Map;

/**
 * Strategy interface for building Reports from raw data.
 * Each strategy knows which keys to expect inside the data Map.
 */
public interface ReportStrategy extends Serializable {

    Report build(Map<String, Object> data);
}