import { Component, OnInit, Input } from '@angular/core';
import { Card } from 'src/app/models/card.model';
import { BlockService } from 'src/app/service/block/block.service';

@Component({
  selector: 'app-admin-card',
  templateUrl: './admin-card.component.html',
  styleUrls: ['./admin-card.component.css']
})
export class AdminCardComponent implements OnInit {
  @Input() card: Card;

  constructor(private blockService: BlockService) { }

  ngOnInit(): void {
  }

  unblockCard() {
    this.blockService.unblockCard(this.card).subscribe(
      _ => this.card.blocked = false
    );
  }

}
