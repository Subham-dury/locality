import React, {useState, useEffect} from "react";
import SelectLocality from "../selectors/LocalitySelector";
import { localitylist } from "../../Data/LocalityList";

const ReviewForm = ({updateSelectedOption ,selectedOption, errMsg, updateReview}) => {

  const setOption = (e) => {
    updateSelectedOption(e.target.value);
  };

  return (
    <form >
       <div class="form-group my-4 text-start">
       <label htmlFor="selectedOption">Select locality</label>
       <select
          className="form-select"
          value={selectedOption}
          id="selectedOption"
          onChange={setOption}
          required
        >
          {localitylist.map((locality) => {
            return (
              <option key={locality.localityId} value={locality.localityId}>
                {locality.name}
              </option>
            );
          })}
        </select>
       </div>
      
      <div class="form-group my-4 text-start">
        <label htmlFor="review">Add review</label>
         <textarea class="form-control" id="exampleFormControlTextarea1" rows="3"
         onChange={(e) => updateReview(e.target.value)}
         ></textarea>
        <small style={{ color: "red", fontSize: "0.8rem" }}>{errMsg}</small>
      </div>
    </form>
  );
};

export default ReviewForm;
