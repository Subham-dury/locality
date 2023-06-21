import React, {useContextt} from "react";
import DataContext from "../../context/DataContext";

const ReviewForm = ({updateSelectedOption ,selectedOption, errMsg, updateReview}) => {

  const {localitylist} = useContextt(DataContext)

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
