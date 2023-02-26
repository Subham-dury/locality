import React from "react";

const FeatureCard = () => {
  return (
    <div class="card m-3">
      <div class="row g-0">
        <div class="col-md-5">
          <div className="card-img">
            <img
              src={require(`../../assests/review-holder-${1}.jpg`)}
              class="rounded-start img-fluid"
              alt="locality image"
            />
          </div>
        </div>
        <div class="col-md-7">
          <div class="card-body">
            <h5 class="card-title">Card title</h5>
            <p class="card-text">
              Lorem ipsum dolor sit amet consectetur adipisicing elit. Possimus
              ullam rerum, nobis vero aliquid aspernatur itaque ea quia dolorum
              alias. Sunt aliquid tenetur pariatur nisi dolorum, labore magnam
              blanditiis sit error quibusdam nihil officiis facere et modi
              expedita, id earum qui sint laudantium voluptas. Quam delectus
              minima mollitia accusantium vero!
            </p>
            <p class="card-text">
              Lorem ipsum dolor sit amet consectetur adipisicing elit. Possimus
              ullam rerum, nobis vero aliquid aspernatur itaque ea quia dolorum
              alias. Sunt aliquid tenetur pariatur nisi dolorum, labore magnam
              blanditiis sit error quibusdam nihil officiis facere et modi
              expedita, id earum qui sint laudantium voluptas. Quam delectus
              minima mollitia accusantium vero!
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default FeatureCard;
